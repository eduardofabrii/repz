package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import repz.app.dto.request.FrequenciaCreateRequest;
import repz.app.dto.response.AlunoInativoResponse;
import repz.app.dto.response.FrequenciaRelatorioResponse;
import repz.app.dto.response.FrequenciaResponse;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Check-ins", description = "Registro e consulta de frequência dos alunos")
@SecurityRequirement(name = "bearer-jwt")
@RequestMapping("/api/checkins")
public interface FrequenciaController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar check-in", description = "Registra a presença de um aluno em uma academia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Check-in registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    FrequenciaResponse criar(
            @Valid @RequestBody FrequenciaCreateRequest request,
            @Parameter(description = "ID da academia no contexto da requisição", example = "1")
            @RequestHeader(value = "X-Academia-Id", required = false) Long academiaId,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping
    @Operation(summary = "Listar check-ins", description = "Filtra check-ins por aluno, academia e período.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-ins encontrados"),
            @ApiResponse(responseCode = "400", description = "Filtro obrigatório ausente"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    List<FrequenciaResponse> findAll(
            @Parameter(description = "ID do aluno para filtro", example = "1")
            @RequestParam(required = false) Long aluno,
            @Parameter(description = "ID da academia para filtro", example = "1")
            @RequestParam(required = false) Long academia,
            @Parameter(description = "Data/hora inicial", example = "2026-05-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final", example = "2026-05-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @Parameter(description = "ID da academia no contexto da requisição", example = "1")
            @RequestHeader(value = "X-Academia-Id", required = false) Long academiaHeader,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar check-in", description = "Retorna os dados de um check-in pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado")
    })
    FrequenciaResponse findById(
            @Parameter(description = "ID do check-in", example = "1")
            @PathVariable Long id);

    @GetMapping("/me")
    @Operation(summary = "Meu histórico", description = "Lista o histórico de check-ins do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    List<FrequenciaResponse> meuHistorico(@Parameter(hidden = true) Authentication auth);

    @GetMapping("/alunos/inativos")
    @Operation(summary = "Listar alunos inativos", description = "Lista alunos sem check-in recente em uma academia.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alunos inativos encontrados"),
            @ApiResponse(responseCode = "400", description = "Academia não informada"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    List<AlunoInativoResponse> alunosInativos(
            @Parameter(description = "ID da academia", example = "1")
            @RequestParam(required = false) Long academia,
            @Parameter(description = "ID da academia no contexto da requisição", example = "1")
            @RequestHeader(value = "X-Academia-Id", required = false) Long academiaHeader,
            @Parameter(hidden = true) Authentication auth);

    @GetMapping("/relatorio")
    @Operation(summary = "Gerar relatório", description = "Gera um relatório de check-ins por academia e período.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    FrequenciaRelatorioResponse obterRelatorio(
            @Parameter(description = "ID da academia", example = "1")
            @RequestParam(required = false) Long academia,
            @Parameter(description = "Data/hora inicial", example = "2026-05-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final", example = "2026-05-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            @Parameter(description = "ID da academia no contexto da requisição", example = "1")
            @RequestHeader(value = "X-Academia-Id", required = false) Long academiaHeader,
            @Parameter(hidden = true) Authentication auth);

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar check-in", description = "Reativa um check-in desativado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in ativado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado")
    })
    void ativar(
            @Parameter(description = "ID do check-in", example = "1")
            @PathVariable Long id);

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar check-in", description = "Desativa um check-in por soft delete.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in desativado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado")
    })
    void desativar(
            @Parameter(description = "ID do check-in", example = "1")
            @PathVariable Long id);

}
