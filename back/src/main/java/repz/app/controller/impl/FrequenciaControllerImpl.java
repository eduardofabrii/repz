package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.FrequenciaController;
import repz.app.dto.request.FrequenciaCreateRequest;
import repz.app.dto.response.AlunoInativoResponse;
import repz.app.dto.response.FrequenciaRelatorioResponse;
import repz.app.dto.response.FrequenciaResponse;
import repz.app.service.frequencia.FrequenciaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FrequenciaControllerImpl implements FrequenciaController {

    private final FrequenciaService frequenciaService;

    @Override
    @PreAuthorize("hasAnyRole('USUARIO', 'PERSONAL')")
    public FrequenciaResponse registrarFrequencia(FrequenciaCreateRequest request, Authentication auth) {
        return frequenciaService.registrarFrequencia(request, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<FrequenciaResponse> filtrar(Long aluno, Long academia, LocalDateTime inicio, LocalDateTime fim) {
        if (aluno != null) {
            return frequenciaService.filtrarPorPeriodo(aluno, inicio, fim);
        } else if (academia != null) {
            return frequenciaService.filtrarPorAcademiaEPeriodo(academia, inicio, fim);
        }
        throw new RuntimeException("Parâmetro aluno ou academia deve ser informado");
    }

    @Override
    @PreAuthorize("hasRole('USUARIO')")
    public List<FrequenciaResponse> meuHistorico(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return frequenciaService.meuHistorico(userId);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<AlunoInativoResponse> alunosInativos(Long academia) {
        return frequenciaService.obterAlunosInativos(academia);
    }

    @Override
    @PreAuthorize("hasAnyRole('ACADEMIA', 'ADMIN')")
    public FrequenciaRelatorioResponse obterRelatorio(Long academia, LocalDateTime inicio, LocalDateTime fim, Authentication auth) {
        return frequenciaService.obterRelatorio(academia, inicio, fim, auth);
    }

    @Override
    public void deletar(Long id) {
        frequenciaService.deletar(id);
    }
}



