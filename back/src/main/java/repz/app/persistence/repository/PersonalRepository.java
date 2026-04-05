package repz.app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import repz.app.persistence.entity.Personal;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    
}
