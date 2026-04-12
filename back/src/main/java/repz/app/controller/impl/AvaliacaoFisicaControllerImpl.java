package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.AvaliacaoFisicaController;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;
import repz.app.service.avaliacaoFisica.AvaliacaoFisicaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AvaliacaoFisicaControllerImpl implements AvaliacaoFisicaController {

    private final AvaliacaoFisicaService avaliacaoFisicaService;

    @Override
    @PreAuthorize("hasRole('PERSONAL')")
    public AvaliacaoFisicaResponse registrarAvaliacao(AvaliacaoFisicaCreateRequest request, Authentication auth) {
        return avaliacaoFisicaService.registrarAvaliacao(request, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'USUARIO', 'ACADEMIA', 'ADMIN')")
    public List<AvaliacaoFisicaResponse> obterHistorico(Long aluno, Authentication auth) {
        return avaliacaoFisicaService.obterHistorico(aluno, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'USUARIO', 'ACADEMIA', 'ADMIN')")
    public AvaliacaoFisicaGraficoResponse obterGrafico(Long aluno, Authentication auth) {
        return avaliacaoFisicaService.obterGrafico(aluno, auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('ACADEMIA', 'ADMIN')")
    public List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Authentication auth) {
        return avaliacaoFisicaService.obterDaUnidade(auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('PERSONAL', 'ADMIN')")
    public void deletar(Long id) {
        avaliacaoFisicaService.deletarAvaliacao(id);
    }
}


