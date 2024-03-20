package lechat.server.domain.auth.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import lechat.server.core.exception.CustomException;
import lechat.server.core.exception.ErrorInfo;
import lechat.server.core.security.jwt.JwtProvider;
import lechat.server.core.security.jwt.dto.JwtTokenDto;
import lechat.server.domain.auth.controller.request.JwtTokenReq;
import lechat.server.domain.auth.controller.request.LoginReq;
import lechat.server.domain.auth.controller.request.MemberReq;
import lechat.server.domain.auth.entity.EmailValidation;
import lechat.server.domain.auth.entity.RefreshToken;
import lechat.server.domain.auth.repository.EmailValidationRepository;
import lechat.server.domain.auth.repository.RefreshTokenRepository;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static lechat.server.core.exception.ErrorInfo.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidationRepository emailValidationRepository;
    private final EntityManager em;
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private final JavaMailSender sender;


    @Transactional
    public Member signup(MemberReq memberRequest) {
        memberRepository.findByEmail(memberRequest.getEmail())
                .orElseThrow(() -> new CustomException(EMAIL_ALREADY_EXIST));

        memberRepository.findByName(memberRequest.getName())
                .orElseThrow(() -> new CustomException(NAME_ALREADY_EXIST));

        Member member = memberRequest.createMember(passwordEncoder);
        return memberRepository.save(member);
    }

    /* 회원가입 시, 유효성 체크 */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Transactional
    public JwtTokenDto login(LoginReq loginRequest) {
        // 로그인 credentials을 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        // 사용자 비밀번호 체크
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); // causes 403 error
        // 인증 정보 기반으로 JWT 토큰 생성
        JwtTokenDto tokenDto = jwtProvider.generateToken(authentication);

        // refresh token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 토큰 발급 ~~
        return tokenDto;
    }

    @Transactional
    // 토큰 재발급
    public JwtTokenDto reissue(JwtTokenReq tokenRequest) {
        // refresh token 검증
        if (!jwtProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        // access token에서 memberID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenRequest.getAccessToken());

        // 저장소에서 memberID 기반으로 refresh token 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomException(LOGOUT));

        // refresh token 일치 확인
        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new CustomException(INVALID_TOKEN_CREDENTIALS);
        }

        // 새로운 토큰 생성
        JwtTokenDto tokenDto = jwtProvider.generateToken(authentication);

        // 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }


    @Transactional
    public void sendEmailValidation(String email) throws MessagingException {
        String tokenValue = new String(Base64.getEncoder().encode(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);

        Optional<EmailValidation> emailValidation = emailValidationRepository.findByEmail(email);

        if (emailValidation.isEmpty()) {
            EmailValidation createdEmailValidation = EmailValidation.createEmailValidation(tokenValue, email);
            emailValidationRepository.save(createdEmailValidation);
        } else {
            emailValidation.get().updateEmailValidation(tokenValue);
            em.flush();
            em.clear();
        }

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Confirm you E-Mail - MFA Application Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Dear "+ email + ",</h2>"
                        + "<br/> We're excited to have you get started. " +
                        "Please click on below link to confirm your account."
                        + "<br/> "  + generateConfirmationLink(tokenValue)+"" +
                        "<br/> Regards,<br/>" +
                        "MFA Registration team" +
                        "</body>" +
                        "</html>"
                , true);

        sender.send(message);
    }

    private String generateConfirmationLink(String token){
        final String confirmLink = "http://localhost:8080/api/v1/auth/confirm-email?token="+token;
        return "<a href=" + confirmLink +">Confirm Email</a>";
    }

    public boolean verifyEmail(String token) {
        EmailValidation validation = emailValidationRepository.findByToken(token)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        System.out.println("validation.getToken() = " + validation.getToken());
        System.out.println("token = " + token);
        return true;
    }
}
