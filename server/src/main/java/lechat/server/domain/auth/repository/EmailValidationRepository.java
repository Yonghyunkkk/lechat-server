package lechat.server.domain.auth.repository;

import lechat.server.domain.auth.entity.EmailValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailValidationRepository extends JpaRepository<EmailValidation, Long> {

    Optional<EmailValidation> findByToken(String token);

    Optional<EmailValidation> findByEmail(String email);
}
