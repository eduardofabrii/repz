package repz.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repz.app.dto.academia.AcademiaDashboardDTO;
import repz.app.dto.academia.AcademiaRequestDTO;
import repz.app.dto.academia.AcademiaResponseDTO;

import java.util.List;

@RequestMapping("/academias")
public interface AcademiaController {

    /**
     * RF06: Cadastrar Academia (apenas ADMIN)
     * POST /academias
     */
    @PostMapping
    ResponseEntity<AcademiaResponseDTO> createAcademia(@RequestBody @Valid AcademiaRequestDTO dto);

    /**
     * RF07: Listar todas as academias (apenas ADMIN)
     * GET /academias
     */
    @GetMapping
    ResponseEntity<List<AcademiaResponseDTO>> listAcademias();

    /**
     * RF07: Buscar academia por ID (apenas ADMIN)
     * GET /academias/{id}
     */
    @GetMapping("/{id}")
    ResponseEntity<AcademiaResponseDTO> getAcademiaById(@PathVariable Long id);

    /**
     * RF07: Editar academia (apenas ADMIN)
     * PUT /academias/{id}
     */
    @PutMapping("/{id}")
    ResponseEntity<AcademiaResponseDTO> updateAcademia(
            @PathVariable Long id,
            @RequestBody @Valid AcademiaRequestDTO dto
    );

    /**
     * RF07: Inativar academia (apenas ADMIN)
     * PATCH /academias/{id}
     */
    @PatchMapping("/{id}")
    ResponseEntity<AcademiaResponseDTO> deactivateAcademia(@PathVariable Long id);

    /**
     * RF08: Visualizar dados da própria academia (perfil ACADEMIA)
     * GET /academias/me
     */
    @GetMapping("/me")
    ResponseEntity<AcademiaResponseDTO> getOwnAcademia();

    /**
     * RF08: Editar própria academia (perfil ACADEMIA)
     * PUT /academias/me
     */
    @PutMapping("/me")
    ResponseEntity<AcademiaResponseDTO> updateOwnAcademia(@RequestBody @Valid AcademiaRequestDTO dto);

    /**
     * RF09: Dashboard com métricas (apenas ADMIN)
     * GET /academias/dashboard
     */
    @GetMapping("/dashboard")
    ResponseEntity<AcademiaDashboardDTO> getDashboard();
}
