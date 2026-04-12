package repz.app.service.frequencia;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import repz.app.dto.request.FrequenciaCreateRequest;
import repz.app.dto.response.AlunoInativoResponse;
import repz.app.dto.response.FrequenciaRelatorioResponse;
import repz.app.dto.response.FrequenciaResponse;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.Frequencia;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.FrequenciaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FrequenciaService {
    private final FrequenciaRepository frequenciaRepository;
    private final UserRepository userRepository;
    private final AcademiaRepository academiaRepository;
    private final PersonalRepository personalRepository;

    public FrequenciaResponse registrarFrequencia(FrequenciaCreateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.USUARIO) {
            if (!currentUser.getId().equals(request.getAlunoId())) {
                throw new RuntimeException("Aluno só pode registrar frequência para si mesmo");
            }
        } else if (currentUser.getRole() != UserRole.PERSONAL) {
            throw new RuntimeException("Perfil não autorizado para registrar frequência");
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

        Frequencia frequencia = new Frequencia();
        frequencia.setAluno(aluno);
        frequencia.setAcademia(academia);
        frequencia.setRegistradoPor(registradoPor);
        frequencia.setDataHora(request.getDataHora() != null ? request.getDataHora() : LocalDateTime.now());

        Frequencia saved = frequenciaRepository.save(frequencia);
        return toDTO(saved);
    }

    public List<FrequenciaResponse> filtrarPorPeriodo(Long alunoId, LocalDateTime inicio, LocalDateTime fim) {
        List<Frequencia> frequencias = frequenciaRepository.findByAlunoIdAndPeriodo(alunoId, inicio, fim);
        return frequencias.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<FrequenciaResponse> filtrarPorAcademiaEPeriodo(Long academiaId, LocalDateTime inicio, LocalDateTime fim) {
        List<Frequencia> frequencias = frequenciaRepository.findByAcademiaIdAndPeriodo(academiaId, inicio, fim);
        return frequencias.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<FrequenciaResponse> meuHistorico(Long alunoId) {
        List<Frequencia> frequencias = frequenciaRepository.findByAluno_IdOrderByDataHoraDesc(alunoId);
        return frequencias.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<AlunoInativoResponse> obterAlunosInativos(Long academiaId) {
        academiaRepository.findById(academiaId)
                .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

        List<Frequencia> frequencias = frequenciaRepository.findAll().stream()
                .filter(f -> f.getAcademia().getId().equals(academiaId))
                .collect(Collectors.toList());

        return frequencias.stream()
                .collect(Collectors.groupingBy(f -> f.getAluno()))
                .entrySet().stream()
                .map(entry -> {
                    User aluno = entry.getKey();
                    List<Frequencia> frequenciasList = entry.getValue();
                    LocalDateTime lastFrequencia = frequenciasList.stream()
                            .map(Frequencia::getDataHora)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);

                    long diasSemTreino = lastFrequencia != null
                            ? ChronoUnit.DAYS.between(lastFrequencia, LocalDateTime.now())
                            : Long.MAX_VALUE;

                    return new AlunoInativoResponse(
                            aluno.getId(),
                            aluno.getName(),
                            aluno.getEmail(),
                            diasSemTreino,
                            diasSemTreino <= 7
                    );
                })
                .filter(response -> response.getDiasSemTreino() > 7)
                .collect(Collectors.toList());
    }

    public FrequenciaRelatorioResponse obterRelatorio(Long academiaId, LocalDateTime inicio, LocalDateTime fim, Authentication auth) {
        String email = ((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal()).getUsername();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() != UserRole.ACADEMIA && currentUser.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Acesso negado ao relatório");
        }

        List<Frequencia> frequencias = frequenciaRepository.findByAcademiaIdAndPeriodo(academiaId, inicio, fim);

        var frequenciaPorAluno = frequencias.stream()
                .collect(Collectors.groupingByConcurrent(
                        f -> f.getAluno().getName(),
                        Collectors.counting()
                ));

        FrequenciaRelatorioResponse response = new FrequenciaRelatorioResponse();
        response.setAcademiaId(academiaId);
        response.setPeriodo(java.util.Map.of("inicio", inicio, "fim", fim));
        response.setTotalFrequencias((long) frequencias.size());
        response.setFrequenciaPorAluno(frequenciaPorAluno);

        return response;
    }

    public void deletar(Long id) {
        frequenciaRepository.deleteById(id);
    }


    private FrequenciaResponse toDTO(Frequencia frequencia) {
        return new FrequenciaResponse(
                frequencia.getId(),
                frequencia.getDataHora(),
                frequencia.getAluno().getId(),
                frequencia.getAluno().getName(),
                frequencia.getAcademia().getId(),
                frequencia.getAcademia().getName(),
                frequencia.getRegistradoPor() != null ? frequencia.getRegistradoPor().getUser().getId() : null,
                frequencia.getRegistradoPor() != null ? frequencia.getRegistradoPor().getUser().getName() : null
        );
    }
}


