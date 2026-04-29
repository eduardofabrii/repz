package repz.app.controller;

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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequestMapping("/academias")
public interface AcademiaController {

    @PostMapping
    @SecurityRequirement(name = "bearer-jwt")
    ResponseEntity<AcademiaResponse> criar(@RequestBody @Valid AcademiaCreateRequest dto);

    @GetMapping
    ResponseEntity<List<AcademiaResponse>> listar();

    @GetMapping("/{id}")
    ResponseEntity<AcademiaResponse> obterPorId(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<AcademiaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AcademiaUpdateRequest dto
    );

    @PatchMapping("/{id}")
    ResponseEntity<AcademiaResponse> inativar(@PathVariable Long id);

    @GetMapping("/me")
    ResponseEntity<AcademiaResponse> obterMinha();

    @PutMapping("/me")
    ResponseEntity<AcademiaResponse> atualizarMinha(@RequestBody @Valid AcademiaUpdateRequest dto);

    @GetMapping("/dashboard")
    ResponseEntity<AcademiaDashboardResponse> obterDashboard();
}
