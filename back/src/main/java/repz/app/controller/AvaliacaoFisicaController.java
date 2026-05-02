package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;

import java.util.List;

@Tag(name = "Avaliações Físicas", description = "Registro e acompanhamento de avaliações físicas")
@RequestMapping("/api/avaliacoes")
public interface AvaliacaoFisicaController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar avaliação", description = "Registra uma avaliação física e calcula o IMC automaticamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    AvaliacaoFisicaResponse criar(
            @Valid @RequestBody AvaliacaoFisicaCreateRequest request,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping
    @Operation(summary = "Listar avaliações", description = "Lista o histórico de avaliações de um aluno.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    List<AvaliacaoFisicaResponse> findAll(
            @Parameter(description = "ID do aluno", example = "1")
            @RequestParam Long aluno,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação", description = "Retorna os dados de uma avaliação física pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    AvaliacaoFisicaResponse findById(
            @Parameter(description = "ID da avaliação", example = "1")
            @PathVariable Long id);

    @GetMapping("/grafico")
    @Operation(summary = "Gerar gráfico", description = "Retorna dados de evolução física para gráficos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados do gráfico retornados"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    AvaliacaoFisicaGraficoResponse obterGrafico(
            @Parameter(description = "ID do aluno", example = "1")
            @RequestParam Long aluno,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping("/unidade")
    @Operation(summary = "Listar avaliações da unidade", description = "Lista avaliações de alunos de uma academia.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(
            @Parameter(description = "ID da academia no contexto da requisição", example = "1")
            @RequestHeader(value = "X-Academia-Id", required = false) Long academiaId,
            @Parameter(hidden = true) Authentication auth);

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar avaliação", description = "Reativa uma avaliação física desativada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação ativada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    void ativar(
            @Parameter(description = "ID da avaliação", example = "1")
            @PathVariable Long id);

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar avaliação", description = "Desativa uma avaliação física por soft delete.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação desativada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    void desativar(
            @Parameter(description = "ID da avaliação", example = "1")
            @PathVariable Long id);

}
