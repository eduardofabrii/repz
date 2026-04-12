package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repz.app.dto.request.FrequenciaCreateRequest;
import repz.app.dto.response.AlunoInativoResponse;
import repz.app.dto.response.FrequenciaRelatorioResponse;
import repz.app.dto.response.FrequenciaResponse;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/checkins")
@Tag(name = "Frequência", description = "Gerenciar frequência e check-ins de alunos")
public interface FrequenciaController {

    @PostMapping
    @Operation(summary = "Registrar frequência", description = "Registrar check-in/frequência do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Frequência registrada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    FrequenciaResponse registrarFrequencia(@RequestBody FrequenciaCreateRequest request, Authentication auth);

    @GetMapping
    @Operation(summary = "Filtrar frequências", description = "Buscar frequências por período e filtro")
    @ApiResponse(responseCode = "200", description = "Lista de frequências")
    List<FrequenciaResponse> filtrar(
            @Parameter(description = "ID do aluno para filtro")
            @RequestParam(required = false) Long aluno,
            @Parameter(description = "ID da academia para filtro")
            @RequestParam(required = false) Long academia,
            @Parameter(description = "Data/hora inicial (YYYY-MM-DDTHH:MM:SS)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final (YYYY-MM-DDTHH:MM:SS)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim);

    @GetMapping("/me")
    @Operation(summary = "Meu histórico", description = "Ver histórico de frequências do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Histórico de frequências")
    List<FrequenciaResponse> meuHistorico(Authentication auth);

    @GetMapping("/alunos/inativos")
    @Operation(summary = "Alunos inativos", description = "Sinalizar alunos sem treino há mais de 7 dias")
    @ApiResponse(responseCode = "200", description = "Lista de alunos inativos")
    List<AlunoInativoResponse> alunosInativos(
            @Parameter(description = "ID da academia", required = true)
            @RequestParam Long academia);

    @GetMapping("/relatorio")
    @Operation(summary = "Relatório de frequência", description = "Gerar relatório de frequência por período")
    @ApiResponse(responseCode = "200", description = "Relatório com frequências agrupadas")
    FrequenciaRelatorioResponse obterRelatorio(
            @Parameter(description = "ID da academia", required = true)
            @RequestParam Long academia,
            @Parameter(description = "Data/hora inicial", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Authentication auth);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar frequência", description = "Remover frequência por ID")
    @ApiResponse(responseCode = "200", description = "Frequência deletada")
    void deletar(@Parameter(description = "ID da frequência") @RequestParam Long id);
}


