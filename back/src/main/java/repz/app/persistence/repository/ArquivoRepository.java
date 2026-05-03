package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repz.app.persistence.entity.Arquivo;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    Optional<Arquivo> findByUserId(Long userId);
}
