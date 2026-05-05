package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repz.app.persistence.entity.Aluno;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByAcademiaId(Long academiaId);

    List<Aluno> findByPersonalId(Long personalId);

    Optional<Aluno> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndAcademiaId(Long usuarioId, Long academiaId);
}
