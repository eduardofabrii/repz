package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;

import java.util.List;

@RequestMapping("/avaliacoes")
@Tag(name = "Avaliação Física", description = "Gerenciar avaliações físicas dos alunos")
public interface AvaliacaoFisicaController {

    @PostMapping
    @Operation(summary = "Registrar avaliação", description = "Registrar avaliação física (peso, altura, % gordura, medidas) com IMC calculado automaticamente")
    @ApiResponse(responseCode = "200", description = "Avaliação registrada com sucesso")
    AvaliacaoFisicaResponse registrarAvaliacao(@Valid @RequestBody AvaliacaoFisicaCreateRequest request, Authentication auth);

    @GetMapping
    @Operation(summary = "Histórico de avaliações", description = "Obter histórico de avaliações em ordem cronológica")
    @ApiResponse(responseCode = "200", description = "Histórico de avaliações")
    List<AvaliacaoFisicaResponse> obterHistorico(
            @Parameter(description = "ID do aluno", required = true)
            @RequestParam Long aluno,
            Authentication auth);

    @GetMapping("/grafico")
    @Operation(summary = "Dados para gráfico", description = "Obter dados para gráfico comparativo de evolução")
    @ApiResponse(responseCode = "200", description = "Dados para gráfico evolutivo")
    AvaliacaoFisicaGraficoResponse obterGrafico(
            @Parameter(description = "ID do aluno", required = true)
            @RequestParam Long aluno,
            Authentication auth);

    @GetMapping("/unidade")
    @Operation(summary = "Avaliações da unidade", description = "Visualizar avaliações de todos os alunos da unidade (somente leitura)")
    @ApiResponse(responseCode = "200", description = "Avaliações da unidade")
    List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Authentication auth);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação", description = "Remover avaliação física")
    @ApiResponse(responseCode = "200", description = "Avaliação deletada")
    void deletar(@Parameter(description = "ID da avaliação") @PathVariable Long id);
}


