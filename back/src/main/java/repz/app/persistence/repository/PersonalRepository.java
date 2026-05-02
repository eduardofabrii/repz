package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repz.app.persistence.entity.Personal;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional<Personal> findByUserId(Long userId);

}
