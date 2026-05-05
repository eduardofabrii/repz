package repz.app.service.aluno;

import org.springframework.security.core.Authentication;
import repz.app.dto.request.AlunoCreateRequest;
import repz.app.dto.request.AlunoMeUpdateRequest;
import repz.app.dto.request.AlunoUpdateRequest;
import repz.app.dto.response.AlunoDetalheResponse;

import java.util.List;

public interface AlunoService {

    AlunoDetalheResponse matricular(AlunoCreateRequest request, Long academiaHeaderId, Authentication auth);

    List<AlunoDetalheResponse> findAll(Long academiaHeaderId, Authentication auth);

    AlunoDetalheResponse findById(Long id, Authentication auth);

    AlunoDetalheResponse atualizar(Long id, AlunoUpdateRequest request, Long academiaHeaderId, Authentication auth);

    void inativar(Long id, Long academiaHeaderId, Authentication auth);

    AlunoDetalheResponse obterMeuPerfil(Authentication auth);

    AlunoDetalheResponse atualizarMeuPerfil(AlunoMeUpdateRequest request, Authentication auth);
}
