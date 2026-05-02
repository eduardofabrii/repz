package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repz.app.persistence.entity.Plano;
import repz.app.persistence.entity.User;

import java.util.List;
import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    List<Plano> findByAcademia(User academia);

    Optional<Plano> findByIdAndAcademia(Integer id, User academia);
}