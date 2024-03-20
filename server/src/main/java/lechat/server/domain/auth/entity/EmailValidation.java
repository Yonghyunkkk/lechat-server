package lechat.server.domain.auth.entity;

import jakarta.persistence.*;
import lechat.server.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class EmailValidation {

    @Id @GeneratedValue
    @Column(name = "email_validation_id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

//    @Setter
//    @OneToOne(mappedBy = "email_validation", fetch = LAZY)
//    private Member member;

    public static EmailValidation createEmailValidation(
            String token,
            String email
    ) {
        EmailValidation validation = EmailValidation.builder()
                .token(token)
                .email(email)
                .build();

        return validation;
    }

    public void updateEmailValidation(
            String token
    ) {
        this.token = token;
    }

}
