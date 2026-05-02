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
    public FrequenciaResponse criar(FrequenciaCreateRequest request, Long academiaId, Authentication auth) {
        return frequenciaService.criar(request, academiaId, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<FrequenciaResponse> findAll(
            Long aluno,
            Long academia,
            LocalDateTime inicio,
            LocalDateTime fim,
            Long academiaHeader,
            Authentication auth) {
        Long academiaId = academiaHeader != null ? academiaHeader : academia;
        if (aluno != null) {
            return frequenciaService.filtrarPorPeriodo(aluno, academiaId, inicio, fim, auth);
        } else if (academiaId != null) {
            return frequenciaService.filtrarPorAcademiaEPeriodo(academiaId, inicio, fim, auth);
        }
        throw new RuntimeException("Parâmetro aluno, academia ou header X-Academia-Id deve ser informado");
    }

    @Override
    @PreAuthorize("hasAnyRole('USUARIO', 'PERSONAL', 'ACADEMIA', 'ADMIN')")
    public FrequenciaResponse findById(Long id) {
        return frequenciaService.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('USUARIO')")
    public List<FrequenciaResponse> meuHistorico(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return frequenciaService.meuHistorico(userId);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<AlunoInativoResponse> alunosInativos(Long academia, Long academiaHeader, Authentication auth) {
        Long academiaId = academiaHeader != null ? academiaHeader : academia;
        return frequenciaService.obterAlunosInativos(academiaId, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('ACADEMIA', 'ADMIN')")
    public FrequenciaRelatorioResponse obterRelatorio(
            Long academia,
            LocalDateTime inicio,
            LocalDateTime fim,
            Long academiaHeader,
            Authentication auth) {
        Long academiaId = academiaHeader != null ? academiaHeader : academia;
        return frequenciaService.obterRelatorio(academiaId, inicio, fim, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public void ativar(Long id) {
        frequenciaService.ativar(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public void desativar(Long id) {
        frequenciaService.desativar(id);
    }

}
