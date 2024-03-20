package lechat.server.domain.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lechat.server.core.security.jwt.dto.JwtTokenDto;
import lechat.server.domain.auth.controller.request.EmailValReq;
import lechat.server.domain.auth.controller.request.JwtTokenReq;
import lechat.server.domain.auth.controller.request.LoginReq;
import lechat.server.domain.auth.controller.request.MemberReq;
import lechat.server.domain.auth.service.AuthService;
import lechat.server.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "1. Authorization")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create user account")
    public ResponseEntity<Member> signup(@Valid @RequestBody MemberReq memberRequest, Errors errors) {
        if (errors.hasErrors()) {
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = authService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                throw new IllegalStateException(key);
            }
        }
        return ResponseEntity.ok(authService.signup(memberRequest));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "user login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginReq loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "reissue access token ")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody JwtTokenReq tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }

    @PostMapping("/validation-code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Send email validation")
    public void sendEmailValidation(@RequestBody EmailValReq request) throws MessagingException {
        authService.sendEmailValidation(request.getEmail());
    }

    @GetMapping("/confirm-email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Confirm email validation")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);

        return ResponseEntity.ok("Yes");
    }
}
