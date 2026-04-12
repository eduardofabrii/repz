package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import repz.app.persistence.entity.Frequencia;

import java.time.LocalDateTime;
import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {

    @Query("SELECT f FROM Frequencia f WHERE f.aluno.id = :alunoId AND f.dataHora BETWEEN :inicio AND :fim ORDER BY f.dataHora DESC")
    List<Frequencia> findByAlunoIdAndPeriodo(@Param("alunoId") Long alunoId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT f FROM Frequencia f WHERE f.academia.id = :academiaId AND f.dataHora BETWEEN :inicio AND :fim ORDER BY f.dataHora DESC")
    List<Frequencia> findByAcademiaIdAndPeriodo(@Param("academiaId") Long academiaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    List<Frequencia> findByAluno_IdOrderByDataHoraDesc(Long alunoId);

    @Query(value = "SELECT f.* FROM frequencia f WHERE f.id_academia = :academiaId ORDER BY f.data_hora DESC LIMIT 1 OFFSET 0", nativeQuery = true)
    Frequencia findLatestByAcademia(@Param("academiaId") Long academiaId);
}
