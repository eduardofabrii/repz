package repz.app.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import repz.app.persistence.entity.AvaliacaoFisica;
import repz.app.service.avaliacaoFisica.AvaliacaoFisicaService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {
    
    private final AvaliacaoFisicaService avaliacaoFisicaService;

    public AvaliacaoFisicaController(AvaliacaoFisicaService avaliacaoFisicaService) {
        this.avaliacaoFisicaService = avaliacaoFisicaService;
    }

// F27: POST /avaliacoes — Registrar avaliação física (peso, altura, % gordura, medidas) — PERSONAL, somente seus alunos
// RF28: IMC calculado automaticamente a partir de peso e altura (peso / altura_m²)
// RF29: GET /avaliacoes?aluno={id} — Histórico em ordem cronológica (PERSONAL vê todos os seus; USUÁRIO somente os seus)
// RF30: GET /avaliacoes/grafico?aluno={id} — Dados para gráfico comparativo de evolução
// RF31: GET /avaliacoes/unidade — Visualizar avaliações de todos os alunos da unidade (ACADEMIA e ADMIN — somente leitura)

    @GetMapping
    public List<AvaliacaoFisica> getAll() {
        return avaliacaoFisicaService.getAll();
    }
    
    @GetMapping("/{id}")
    public AvaliacaoFisica getById(Long id) {
        return avaliacaoFisicaService.getAvaliacaoById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        avaliacaoFisicaService.deleteAvaliacao(id);
    }

    public AvaliacaoFisica upgrade(Long id, AvaliacaoFisica avaliacaoFisica) {
        return avaliacaoFisicaService.upgradeAvaliacaoFisica(id, avaliacaoFisica);
    }

}
