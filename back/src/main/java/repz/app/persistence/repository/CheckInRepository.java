package repz.app.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import repz.app.persistence.entity.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    
    @Query("SELECT c FROM CheckIn c WHERE c.aluno.id = :alunoId AND c.dataHora BETWEEN :inicio AND :fim ORDER BY c.dataHora DESC")
    List<CheckIn> findByAlunoIdAndPeriodo(@Param("alunoId") Long alunoId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT c FROM CheckIn c WHERE c.academia.id = :academiaId AND c.dataHora BETWEEN :inicio AND :fim ORDER BY c.dataHora DESC")
    List<CheckIn> findByAcademiaIdAndPeriodo(@Param("academiaId") Long academiaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    List<CheckIn> findByAluno_IdOrderByDataHoraDesc(Long alunoId);
    
    @Query(value = "SELECT c.* FROM checkin c WHERE c.id_academia = :academiaId ORDER BY c.data_hora DESC LIMIT 1 OFFSET 0", nativeQuery = true)
    CheckIn findLatestByAcademia(@Param("academiaId") Long academiaId);
}  

