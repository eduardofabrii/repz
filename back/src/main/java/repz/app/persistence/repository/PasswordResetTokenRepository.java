package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repz.app.persistence.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
