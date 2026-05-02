package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
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
    public AvaliacaoFisicaResponse criar(AvaliacaoFisicaCreateRequest request, Authentication auth) {
        return avaliacaoFisicaService.criar(request, auth);
    }

    @Override
    public List<AvaliacaoFisicaResponse> findAll(Long aluno, Authentication auth) {
        return avaliacaoFisicaService.findAll(aluno, auth);
    }

    @Override
    public AvaliacaoFisicaResponse findById(Long id) {
        return avaliacaoFisicaService.findById(id);
    }

    @Override
    public AvaliacaoFisicaGraficoResponse obterGrafico(Long aluno, Authentication auth) {
        return avaliacaoFisicaService.obterGrafico(aluno, auth);
    }

    @Override
    public List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Long academiaId, Authentication auth) {
        return avaliacaoFisicaService.obterDaUnidade(academiaId, auth);
    }

    @Override
    public void ativar(Long id) {
        avaliacaoFisicaService.ativar(id);
    }

    @Override
    public void desativar(Long id) {
        avaliacaoFisicaService.desativar(id);
    }

}
