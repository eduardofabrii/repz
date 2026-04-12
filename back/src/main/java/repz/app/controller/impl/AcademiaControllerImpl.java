package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.AcademiaController;
import repz.app.dto.academia.AcademiaDashboardDTO;
import repz.app.dto.academia.AcademiaRequestDTO;
import repz.app.dto.academia.AcademiaResponseDTO;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.service.academia.AcademiaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AcademiaControllerImpl implements AcademiaController {

    private final AcademiaService academiaService;

    @Override
    public ResponseEntity<AcademiaResponseDTO> createAcademia(AcademiaRequestDTO dto) {
        validateAdminRole();
        AcademiaResponseDTO response = academiaService.createAcademia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<AcademiaResponseDTO>> listAcademias() {
        validateAdminRole();
        List<AcademiaResponseDTO> academias = academiaService.listAllAcademias();
        return ResponseEntity.ok(academias);
    }

    @Override
    public ResponseEntity<AcademiaResponseDTO> getAcademiaById(Long id) {
        validateAdminRole();
        AcademiaResponseDTO academia = academiaService.getAcademiaById(id);
        return ResponseEntity.ok(academia);
    }

    @Override
    public ResponseEntity<AcademiaResponseDTO> updateAcademia(Long id, AcademiaRequestDTO dto) {
        validateAdminRole();
        AcademiaResponseDTO updated = academiaService.updateAcademia(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<AcademiaResponseDTO> deactivateAcademia(Long id) {
        validateAdminRole();
        AcademiaResponseDTO deactivated = academiaService.deactivateAcademia(id);
        return ResponseEntity.ok(deactivated);
    }

    @Override
    public ResponseEntity<AcademiaResponseDTO> getOwnAcademia() {
        User currentUser = getCurrentUser();
        validateAcademiaRole(currentUser);
        AcademiaResponseDTO academia = academiaService.getOwnAcademia(currentUser);
        return ResponseEntity.ok(academia);
    }

    @Override
    public ResponseEntity<AcademiaResponseDTO> updateOwnAcademia(AcademiaRequestDTO dto) {
        User currentUser = getCurrentUser();
        validateAcademiaRole(currentUser);
        AcademiaResponseDTO updated = academiaService.updateOwnAcademia(currentUser, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<AcademiaDashboardDTO> getDashboard() {
        validateAdminRole();
        AcademiaDashboardDTO dashboard = academiaService.getDashboardMetrics();
        return ResponseEntity.ok(dashboard);
    }

    // Métodos auxiliares

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
