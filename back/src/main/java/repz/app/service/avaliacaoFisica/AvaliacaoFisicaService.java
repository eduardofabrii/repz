package repz.app.service.avaliacaoFisica;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.AvaliacaoFisica;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.AvaliacaoFisicaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final AcademiaRepository academiaRepository;

    public AvaliacaoFisicaResponse registrarAvaliacao(AvaliacaoFisicaCreateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() != UserRole.PERSONAL) {
            throw new RuntimeException("Apenas PERSONAL pode registrar avaliações");
        }

        Personal personal = personalRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        User aluno = userRepository.findById(Math.toIntExact(request.getAlunoId()))
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        AvaliacaoFisica avaliacao = new AvaliacaoFisica();
        avaliacao.setAluno(aluno);
        avaliacao.setPersonal(personal);
        avaliacao.setAcademia(personal.getAcademia());
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setPesoKg(request.getPesoKg());
        avaliacao.setAlturaCm(request.getAlturaCm());
        avaliacao.setPercentualGordura(request.getPercentualGordura());
        avaliacao.setMedidas(request.getMedidas());

        Double imc = calcularIMC(request.getPesoKg(), request.getAlturaCm());
        avaliacao.setImc(imc);

        AvaliacaoFisica saved = avaliacaoFisicaRepository.save(avaliacao);
        return toDTO(saved);
    }

    public List<AvaliacaoFisicaResponse> obterHistorico(Long alunoId, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.USUARIO && !currentUser.getId().equals(alunoId)) {
            throw new RuntimeException("USUARIO só pode ver suas próprias avaliações");
        }

        if (currentUser.getRole() == UserRole.PERSONAL) {
            Personal personal = personalRepository.findAll().stream()
                    .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Personal não encontrado"));
        }

        List<AvaliacaoFisica> avaliacoes = avaliacaoFisicaRepository.findByAluno_IdOrderByDataAvaliacaoDesc(alunoId);
        return avaliacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AvaliacaoFisicaGraficoResponse obterGrafico(Long alunoId, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.USUARIO && !currentUser.getId().equals(alunoId)) {
            throw new RuntimeException("USUARIO só pode ver seus próprios gráficos");
        }

        User aluno = userRepository.findById(Math.toIntExact(alunoId))
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        List<AvaliacaoFisica> avaliacoes = avaliacaoFisicaRepository.findByAluno_IdOrderByDataAvaliacaoAsc(alunoId);

        List<AvaliacaoFisicaGraficoResponse.DadoGrafico> dados = avaliacoes.stream()
                .map(a -> new AvaliacaoFisicaGraficoResponse.DadoGrafico(
                        a.getDataAvaliacao(),
                        a.getPesoKg(),
                        a.getImc(),
                        a.getPercentualGordura()
                ))
                .collect(Collectors.toList());

        AvaliacaoFisicaGraficoResponse response = new AvaliacaoFisicaGraficoResponse();
        response.setAlunoId(alunoId);
        response.setAlunoNome(aluno.getName());
        response.setDados(dados);

        return response;
    }

    public List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.ADMIN) {
            return avaliacaoFisicaRepository.findAll().stream()
                    .map(this::toDTOUnidade)
                    .collect(Collectors.toList());
        } else if (currentUser.getRole() == UserRole.ACADEMIA) {
            Academia academia = academiaRepository.findByResponsibleUserId(currentUser.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

            return avaliacaoFisicaRepository.findByAcademia_Id(academia.getId()).stream()
                    .map(this::toDTOUnidade)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("Acesso negado");
    }

    public void deletarAvaliacao(Long id) {
        if (!avaliacaoFisicaRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        avaliacaoFisicaRepository.deleteById(id);
    }

    private Double calcularIMC(Double pesoKg, Double alturaCm) {
        if (pesoKg == null || alturaCm == null || alturaCm == 0) {
            return null;
        }
        Double alturaM = alturaCm / 100.0;
        return pesoKg / (alturaM * alturaM);
    }

    private AvaliacaoFisicaResponse toDTO(AvaliacaoFisica avaliacao) {
        return new AvaliacaoFisicaResponse(
                avaliacao.getId(),
                avaliacao.getAluno().getId(),
                avaliacao.getAluno().getName(),
                avaliacao.getPersonal() != null ? avaliacao.getPersonal().getId() : null,
                avaliacao.getPersonal() != null ? avaliacao.getPersonal().getUser().getName() : null,
                avaliacao.getAcademia() != null ? avaliacao.getAcademia().getId() : null,
                avaliacao.getAcademia() != null ? avaliacao.getAcademia().getName() : null,
                avaliacao.getDataAvaliacao(),
                avaliacao.getPesoKg(),
                avaliacao.getAlturaCm(),
                avaliacao.getImc(),
                avaliacao.getPercentualGordura(),
                avaliacao.getMedidas()
        );
    }

    private AvaliacaoFisicaUnidadeResponse toDTOUnidade(AvaliacaoFisica avaliacao) {
        return new AvaliacaoFisicaUnidadeResponse(
                avaliacao.getId(),
                avaliacao.getAluno().getId(),
                avaliacao.getAluno().getName(),
                avaliacao.getAluno().getEmail(),
                avaliacao.getPersonal() != null ? avaliacao.getPersonal().getId() : null,
                avaliacao.getPersonal() != null ? avaliacao.getPersonal().getUser().getName() : null,
                avaliacao.getDataAvaliacao(),
                avaliacao.getPesoKg(),
                avaliacao.getAlturaCm(),
                avaliacao.getImc(),
                avaliacao.getPercentualGordura()
        );
    }
}
