package lechat.server.domain.auth.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReq {

    @NotBlank(message = "email address must not be left blank")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "Please enter a valid email address.")
    @Schema(defaultValue = "sueeeee1026@gmail.com")
    private String email;

    @NotBlank(message = "nickname must not be left blank")
    private String name;

    @NotBlank(message = "password must not be left blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}$", message = "Password must be of 8-16 characters long, with at least 1 alphabet, at least 1 number and 1 special character.")
    private String password;


    public Member createMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
    }

}
