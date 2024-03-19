package lechat.server.domain.member.entity;

import jakarta.persistence.*;
import lechat.server.domain.auth.entity.EmailValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "email_validation_id")
//    private EmailValidation emailValidation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public void addEmailValidation(EmailValidation emailValidation) {
//        this.emailValidation = emailValidation;
//        emailValidation.setMember(this);
//    }
}
