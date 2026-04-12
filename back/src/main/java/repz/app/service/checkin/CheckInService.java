package repz.app.service.checkin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import repz.app.dto.request.CheckInCreateRequest;
import repz.app.dto.response.CheckInResponse;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.CheckIn;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.CheckInRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

@AllArgsConstructor
@Service
public class CheckInService {
    private final CheckInRepository checkInRepository;
    private final UserRepository userRepository;
    private final AcademiaRepository academiaRepository;
    private final PersonalRepository personalRepository;
    
    public CheckInResponse registrarCheckIn(CheckInCreateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName()) instanceof User ? 
            (User) userRepository.findByEmail(auth.getName()) : null;
        
        if (currentUser == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        if (currentUser.getRole() == UserRole.USUARIO) {
            // Usuário (aluno) só pode registrar para si mesmo
            if (!currentUser.getId().equals(request.getAlunoId())) {
                throw new RuntimeException("Aluno só pode registrar check-in para si mesmo");
            }
        } else if (currentUser.getRole() == UserRole.PERSONAL) {
            // Personal pode registrar para seus alunos
            // Validar se o personal pertence à academia
            User aluno = userRepository.findById(Math.toIntExact(request.getAlunoId()))
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        } else {
            throw new RuntimeException("Perfil não autorizado para registrar check-in");
        }
        
        User aluno = userRepository.findById(Math.toIntExact(request.getAlunoId()))
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Academia academia = academiaRepository.findById(request.getAcademiaId())
            .orElseThrow(() -> new RuntimeException("Academia não encontrada"));
        Personal registradoPor = null;
        if (request.getPersonalId() != null) {
            registradoPor = personalRepository.findById(request.getPersonalId())
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));
        }
        
        CheckIn checkIn = new CheckIn();
        checkIn.setAluno(aluno);
        checkIn.setAcademia(academia);
        checkIn.setRegistradoPor(registradoPor);
        checkIn.setDataHora(request.getDataHora() != null ? request.getDataHora() : LocalDateTime.now());
        
        CheckIn saved = checkInRepository.save(checkIn);
        return toDTO(saved);
    }
    
    // RF33: Filtrar por período
    public List<CheckInResponse> filtrarPorPeriodo(Long alunoId, LocalDateTime inicio, LocalDateTime fim) {
        List<CheckIn> checkIns = checkInRepository.findByAlunoIdAndPeriodo(alunoId, inicio, fim);
        return checkIns.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public List<CheckInResponse> filtrarPorAcademiaEPeriodo(Long academiaId, LocalDateTime inicio, LocalDateTime fim) {
        List<CheckIn> checkIns = checkInRepository.findByAcademiaIdAndPeriodo(academiaId, inicio, fim);
        return checkIns.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    // RF34: Meu histórico de check-ins
    public List<CheckInResponse> meuHistorico(Long alunoId) {
        List<CheckIn> checkIns = checkInRepository.findByAluno_IdOrderByDataHoraDesc(alunoId);
        return checkIns.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    // RF35: Alunos inativos (sem check-in há 7+ dias)
    public List<Map<String, Object>> alunosInativos(Long academiaId) {
        Academia academia = academiaRepository.findById(academiaId)
            .orElseThrow(() -> new RuntimeException("Academia não encontrada"));
        
        // Buscar todos os check-ins da academia e agrupar por aluno
        List<CheckIn> checkIns = checkInRepository.findAll().stream()
            .filter(c -> c.getAcademia().getId().equals(academiaId))
            .collect(Collectors.toList());
        
        return checkIns.stream()
            .collect(Collectors.groupingBy(c -> c.getAluno()))
            .entrySet().stream()
            .map(entry -> {
                User aluno = entry.getKey();
                List<CheckIn> checkInsList = entry.getValue();
                LocalDateTime lastCheckIn = checkInsList.stream()
                    .map(CheckIn::getDataHora)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);
                
                long diasSemTreino = lastCheckIn != null 
                    ? ChronoUnit.DAYS.between(lastCheckIn, LocalDateTime.now())
                    : Long.MAX_VALUE;
                
                return new HashMap<String, Object>() {{
                    put("alunoId", aluno.getId());
                    put("alunoNome", aluno.getName());
                    put("email", aluno.getEmail());
                    put("diasSemTreino", diasSemTreino);
                    put("ativo", diasSemTreino <= 7);
                }};
            })
            .filter(map -> (Long) map.get("diasSemTreino") > 7)
            .collect(Collectors.toList());
    }
    
    // RF36: Relatório de frequência
    public Map<String, Object> relatorioFrequencia(Long academiaId, LocalDateTime inicio, LocalDateTime fim, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currentUser = userRepository.findByEmail(userDetails.getUsername()) instanceof User ? 
            (User) userRepository.findByEmail(userDetails.getUsername()) : null;
        
        if (currentUser == null || (currentUser.getRole() != UserRole.ACADEMIA && currentUser.getRole() != UserRole.ADMIN)) {
            throw new RuntimeException("Acesso negado ao relatório");
        }
        
        List<CheckIn> checkIns = checkInRepository.findByAcademiaIdAndPeriodo(academiaId, inicio, fim);
        
        Map<String, Long> frequenciaPorAluno = checkIns.stream()
            .collect(Collectors.groupingByConcurrent(
                c -> c.getAluno().getName(),
                Collectors.counting()
            ));
        
        return new HashMap<String, Object>() {{
            put("academiaId", academiaId);
            put("periodo", new HashMap<String, LocalDateTime>() {{
                put("inicio", inicio);
                put("fim", fim);
            }});
            put("totalCheckIns", checkIns.size());
            put("frequenciaPorAluno", frequenciaPorAluno);
        }};
    }
    
    // Métodos básicos (compatibilidade com código anterior)
    public List<CheckIn> findAllCheckIns() {
        return checkInRepository.findAll();
    }

    public CheckIn findCheckInById(Long id) {
        return checkInRepository.findById(id).orElseThrow(() -> new RuntimeException("CheckIn not found"));
    }

    public CheckIn saveCheckIn(CheckIn checkIn) {
        return checkInRepository.save(checkIn);
    }

    public void deleteCheckIn(Long id) {
        checkInRepository.deleteById(id);
    }

    public CheckIn updateCheckIn(Long id, CheckIn updatedCheckIn) {
        CheckIn existingCheckIn = checkInRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CheckIn not found"));

        existingCheckIn.setAluno(updatedCheckIn.getAluno());
        existingCheckIn.setAcademia(updatedCheckIn.getAcademia());
        existingCheckIn.setRegistradoPor(updatedCheckIn.getRegistradoPor());
        existingCheckIn.setDataHora(updatedCheckIn.getDataHora());

        return checkInRepository.save(existingCheckIn);
    }
    
    // Método auxiliar privado
    private CheckInResponse toDTO(CheckIn checkIn) {
        return new CheckInResponse(
            checkIn.getId(),
            checkIn.getDataHora(),
            checkIn.getAluno().getId(),
            checkIn.getAluno().getName(),
            checkIn.getAcademia().getId(),
            checkIn.getAcademia().getName(),
            checkIn.getRegistradoPor() != null ? checkIn.getRegistradoPor().getUser().getId() : null,
            checkIn.getRegistradoPor() != null ? checkIn.getRegistradoPor().getUser().getName() : null
        );
    }
}
