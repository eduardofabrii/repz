package repz.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repz.app.dto.request.CheckInCreateRequest;
import repz.app.dto.response.CheckInResponse;
import repz.app.persistence.entity.CheckIn;
import repz.app.service.checkin.CheckInService;

@RestController
@RequestMapping("/checkins")
public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USUARIO', 'PERSONAL')")
    public CheckInResponse registrarCheckIn(@RequestBody CheckInCreateRequest request, Authentication auth) {
        return checkInService.registrarCheckIn(request, auth);
    }

    // RF33: Filtrar por período
    @GetMapping
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<CheckInResponse> filtrarCheckins(
            @RequestParam(required = false) Long aluno,
            @RequestParam(required = false) Long academia,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        if (aluno != null) {
            return checkInService.filtrarPorPeriodo(aluno, inicio, fim);
        } else if (academia != null) {
            return checkInService.filtrarPorAcademiaEPeriodo(academia, inicio, fim);
        }
        throw new RuntimeException("Parâmetro aluno ou academia deve ser informado");
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USUARIO')")
    public List<CheckInResponse> meuHistorico(Authentication auth) {

        // Ajustar isso conforme sua implementação
        Long userId = Long.parseLong(auth.getName()); // Isso precisa ser adaptado para sua estrutura
        return checkInService.meuHistorico(userId);
    }

    // RF35: Alunos inativos (7+ dias sem check-in)
    @GetMapping("/alunos/inativos")
    @PreAuthorize("hasAnyRole('PERSONAL', 'ACADEMIA', 'ADMIN')")
    public List<Map<String, Object>> alunosInativos(
            @RequestParam Long academia) {
        return checkInService.alunosInativos(academia);
    }

    // Relatório de frequência
    @GetMapping("/relatorio")
    @PreAuthorize("hasAnyRole('ACADEMIA', 'ADMIN')")
    public Map<String, Object> relatorioFrequencia(
            @RequestParam Long academia,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Authentication auth) {
        return checkInService.relatorioFrequencia(academia, inicio, fim, auth);
    }

    @GetMapping("/all")
    public List<CheckIn> getAllCheckIns() {
        return checkInService.findAllCheckIns();
    }

    @GetMapping("/{id}")
    public CheckIn getCheckInById(@RequestParam Long id) {
        return checkInService.findCheckInById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCheckIn(@RequestParam Long id) {
        checkInService.deleteCheckIn(id);
    }
}

