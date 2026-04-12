package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.PersonalController;
import repz.app.dto.request.PersonalCreateRequest;
import repz.app.dto.request.PersonalUpdateRequest;
import repz.app.dto.response.PersonalAlunosResponse;
import repz.app.dto.response.PersonalResponse;
import repz.app.service.personal.PersonalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonalControllerImpl implements PersonalController {

    private final PersonalService personalService;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIA')")
    public PersonalResponse criarPersonal(PersonalCreateRequest request, Authentication auth) {
        return personalService.criarPersonal(request, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIA')")
    public List<PersonalResponse> listarPersonais(Authentication auth) {
        return personalService.listarPersonais(auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIA')")
    public PersonalResponse obterPorId(Long id) {
        return personalService.obterPorId(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIA')")
    public PersonalResponse atualizarPersonal(Long id, PersonalUpdateRequest request, Authentication auth) {
        return personalService.atualizarPersonal(id, request, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACADEMIA')")
    public PersonalResponse inativarPersonal(Long id, Authentication auth) {
        return personalService.inativarPersonal(id, auth);
    }

    @Override
    @PreAuthorize("hasRole('PERSONAL')")
    public PersonalResponse obterMeuPerfil(Authentication auth) {
        return personalService.obterMeuPerfil(auth);
    }

    @Override
    @PreAuthorize("hasRole('PERSONAL')")
    public PersonalResponse atualizarMeuPerfil(PersonalUpdateRequest request, Authentication auth) {
        return personalService.atualizarMeuPerfil(request, auth);
    }

    @Override
    @PreAuthorize("hasRole('PERSONAL')")
    public PersonalAlunosResponse obterMeusAlunos(Authentication auth) {
        return personalService.obterMeusAlunos(auth);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletar(Long id) {
        personalService.deletarPersonal(id);
    }
}


