package repz.app.service.avaliacaoFisica;

import org.springframework.security.core.Authentication;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;

import java.util.List;

public interface AvaliacaoFisicaService {

    AvaliacaoFisicaResponse criar(AvaliacaoFisicaCreateRequest request, Authentication auth);

    List<AvaliacaoFisicaResponse> findAll(Long alunoId, Authentication auth);

    AvaliacaoFisicaResponse findById(Long id);

    AvaliacaoFisicaGraficoResponse obterGrafico(Long alunoId, Authentication auth);

    List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Long academiaHeaderId, Authentication auth);

    void ativar(Long id);

    void desativar(Long id);
}
