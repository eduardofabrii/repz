package repz.app.service.personal;

import java.util.List;

import org.springframework.stereotype.Service;

import repz.app.persistence.entity.Personal;
import repz.app.persistence.repository.PersonalRepository;

@Service
public class PersonalService {

    private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }


    public List<Personal> findAllPersonals() {
        return personalRepository.findAll();
    }

    public Personal findPersonalById(Long id) {
        return personalRepository.findById(id).orElseThrow(() -> new RuntimeException("Personal not found"));
    }

    public Personal savePersonal(Personal personal) {
        return personalRepository.save(personal);
    }

    public void deletePersonal(Long id) {
        personalRepository.deleteById(id);
    }
}
