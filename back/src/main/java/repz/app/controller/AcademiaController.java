package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repz.app.dto.request.AcademiaCreateRequest;
import repz.app.dto.request.AcademiaUpdateRequest;
import repz.app.dto.response.AcademiaDashboardResponse;
import repz.app.dto.response.AcademiaResponse;

import java.util.List;

@Tag(name = "Academias", description = "Cadastro, consulta e gestão de academias")
@RequestMapping("/api/academias")
public interface AcademiaController {

    @PostMapping
    @Operation(summary = "Criar academia", description = "Cadastra uma nova academia. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Academia criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<AcademiaResponse> criar(@RequestBody @Valid AcademiaCreateRequest dto);

    @GetMapping
    @Operation(summary = "Listar academias", description = "Lista todas as academias cadastradas. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academias encontradas"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<List<AcademiaResponse>> findAll();

    @GetMapping("/{id}")
    @Operation(summary = "Buscar academia", description = "Retorna os dados de uma academia pelo ID. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia encontrada"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Academia não encontrada")
    })
    ResponseEntity<AcademiaResponse> findById(
            @Parameter(description = "ID da academia", example = "1")
            @PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar academia", description = "Atualiza os dados de uma academia. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Academia não encontrada")
    })
    ResponseEntity<AcademiaResponse> atualizar(
            @Parameter(description = "ID da academia", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid AcademiaUpdateRequest dto
    );

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar academia", description = "Reativa uma academia desativada. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia ativada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Academia não encontrada")
    })
    ResponseEntity<AcademiaResponse> ativar(
            @Parameter(description = "ID da academia", example = "1")
            @PathVariable Long id);

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar academia", description = "Desativa uma academia por soft delete. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia desativada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Academia não encontrada")
    })
    ResponseEntity<AcademiaResponse> desativar(
            @Parameter(description = "ID da academia", example = "1")
            @PathVariable Long id);

    @GetMapping("/me")
    @Operation(summary = "Minha academia", description = "Retorna os dados da academia do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia encontrada"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<AcademiaResponse> obterMinha();

    @PutMapping("/me")
    @Operation(summary = "Atualizar minha academia", description = "Atualiza os dados da academia do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Academia atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<AcademiaResponse> atualizarMinha(@RequestBody @Valid AcademiaUpdateRequest dto);

    @GetMapping("/dashboard")
    @Operation(summary = "Consultar dashboard", description = "Retorna indicadores gerais das academias. Requer perfil ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dashboard retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    ResponseEntity<AcademiaDashboardResponse> obterDashboard();
}
