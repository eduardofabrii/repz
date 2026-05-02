package repz.app.service.personal;

import org.springframework.security.core.Authentication;
import repz.app.dto.request.PersonalCreateRequest;
import repz.app.dto.request.PersonalUpdateRequest;
import repz.app.dto.response.PersonalAlunosResponse;
import repz.app.dto.response.PersonalResponse;

import java.util.List;

public interface PersonalService {

    PersonalResponse criar(PersonalCreateRequest request, Long academiaHeaderId, Authentication auth);

    List<PersonalResponse> findAll(Long academiaHeaderId, Authentication auth);

    PersonalResponse findById(Long id);

    PersonalResponse atualizar(Long id, PersonalUpdateRequest request, Long academiaHeaderId, Authentication auth);

    PersonalResponse ativar(Long id, Long academiaHeaderId, Authentication auth);

    PersonalResponse desativar(Long id, Long academiaHeaderId, Authentication auth);

    PersonalResponse obterMeuPerfil(Authentication auth);

    PersonalResponse atualizarMeuPerfil(PersonalUpdateRequest request, Authentication auth);

    PersonalAlunosResponse obterMeusAlunos(Authentication auth);
}
