package repz.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import repz.app.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByName(String name);
    UserDetails findByEmail(String email);
    List<User> findByDeletedAtIsNull();
    Optional<User> findByIdAndDeletedAtIsNull(Integer id);
    List<User> findByActiveTrueAndDeletedAtIsNull();
    List<User> findByActiveAndDeletedAtIsNull(Boolean active);
}