package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repz.app.persistence.entity.AvaliacaoFisica;

import java.util.List;

public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    List<AvaliacaoFisica> findByAluno_IdOrderByDataAvaliacaoDesc(Long alunoId);

    List<AvaliacaoFisica> findByAluno_IdOrderByDataAvaliacaoAsc(Long alunoId);

    List<AvaliacaoFisica> findByAcademia_Id(Long academiaId);
}
