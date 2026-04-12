package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import repz.app.persistence.entity.AvaliacaoFisica;

public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    
}
