package repz.app.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.AcademiaController;
import repz.app.dto.request.AcademiaCreateRequest;
import repz.app.dto.request.AcademiaUpdateRequest;
import repz.app.dto.response.AcademiaDashboardResponse;
import repz.app.dto.response.AcademiaResponse;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.service.academia.AcademiaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Academias", description = "Gerenciar academias (apenas ADMIN)")
public class AcademiaControllerImpl implements AcademiaController {

    private final AcademiaService academiaService;

    @Override
    @Operation(summary = "Criar academia", description = "Cadastrar nova academia (apenas ADMIN)")
    @ApiResponse(responseCode = "201", description = "Academia criada com sucesso")
    public ResponseEntity<AcademiaResponse> criar(AcademiaCreateRequest dto) {
        validateAdminRole();
        AcademiaResponse response = academiaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @Operation(summary = "Listar academias", description = "Listar todas as academias (apenas ADMIN)")
    @ApiResponse(responseCode = "200", description = "Lista de academias")
    public ResponseEntity<List<AcademiaResponse>> listar() {
        validateAdminRole();
        List<AcademiaResponse> academias = academiaService.listarTodas();
        return ResponseEntity.ok(academias);
    }

    @Override
    @Operation(summary = "Obter academia", description = "Buscar academia por ID (apenas ADMIN)")
    @ApiResponse(responseCode = "200", description = "Academia encontrada")
    public ResponseEntity<AcademiaResponse> obterPorId(
            @Parameter(description = "ID da academia") Long id) {
        validateAdminRole();
        AcademiaResponse academia = academiaService.obterPorId(id);
        return ResponseEntity.ok(academia);
    }

    @Override
    @Operation(summary = "Editar academia", description = "Atualizar dados da academia (apenas ADMIN)")
    @ApiResponse(responseCode = "200", description = "Academia atualizada")
    public ResponseEntity<AcademiaResponse> atualizar(
            @Parameter(description = "ID da academia") Long id,
            AcademiaUpdateRequest dto) {
        validateAdminRole();
        AcademiaResponse updated = academiaService.atualizar(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    @Operation(summary = "Inativar academia", description = "Inativar academia (soft delete)")
    @ApiResponse(responseCode = "200", description = "Academia inativada")
    public ResponseEntity<AcademiaResponse> inativar(
            @Parameter(description = "ID da academia") Long id) {
        validateAdminRole();
        AcademiaResponse deactivated = academiaService.inativar(id);
        return ResponseEntity.ok(deactivated);
    }

    @Override
    @Operation(summary = "Minha academia", description = "Visualizar dados da própria academia (perfil ACADEMIA)")
    @ApiResponse(responseCode = "200", description = "Dados da academia")
    public ResponseEntity<AcademiaResponse> obterMinha() {
        User currentUser = getCurrentUser();
        validateAcademiaRole(currentUser);
        AcademiaResponse academia = academiaService.obterMinha(currentUser);
        return ResponseEntity.ok(academia);
    }

    @Override
    @Operation(summary = "Editar minha academia", description = "Atualizar dados da própria academia (perfil ACADEMIA)")
    @ApiResponse(responseCode = "200", description = "Academia atualizada")
    public ResponseEntity<AcademiaResponse> atualizarMinha(AcademiaUpdateRequest dto) {
        User currentUser = getCurrentUser();
        validateAcademiaRole(currentUser);
        AcademiaResponse updated = academiaService.atualizarMinha(currentUser, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    @Operation(summary = "Dashboard", description = "Visualizar dashboard com métricas de todas as academias (apenas ADMIN)")
    @ApiResponse(responseCode = "200", description = "Métricas e dashboard")
    public ResponseEntity<AcademiaDashboardResponse> obterDashboard() {
        validateAdminRole();
        AcademiaDashboardResponse dashboard = academiaService.obterDashboard();
        return ResponseEntity.ok(dashboard);
    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }
        return (User) authentication.getPrincipal();
    }

    private void validateAdminRole() {
        User currentUser = getCurrentUser();
        if (!currentUser.getRole().equals(UserRole.ADMIN)) {
            throw new org.springframework.security.access.AccessDeniedException("Acesso negado: permissão ADMIN necessária");
        }
    }

    private void validateAcademiaRole(User user) {
        if (!user.getRole().equals(UserRole.ACADEMIA)) {
            throw new org.springframework.security.access.AccessDeniedException("Acesso negado: perfil ACADEMIA necessário");
        }
    }
}
