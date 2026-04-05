package repz.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import repz.app.persistence.entity.Personal;
import repz.app.service.personal.PersonalService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/personal")
public class PersonalController {

    private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @GetMapping
    public List<Personal> getAllPersonals() {
        return personalService.findAllPersonals();
    }

    @PostMapping
    public Personal createPersonal(@RequestBody Personal personal) {
        return personalService.savePersonal(personal);
    }
    
    
}
