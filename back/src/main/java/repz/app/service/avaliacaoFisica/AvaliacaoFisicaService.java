package repz.app.service.avaliacaoFisica;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import repz.app.dto.request.AvaliacaoFisicaCreateRequest;
import repz.app.dto.response.AvaliacaoFisicaGraficoResponse;
import repz.app.dto.response.AvaliacaoFisicaResponse;
import repz.app.dto.response.AvaliacaoFisicaUnidadeResponse;
import repz.app.persistence.entity.AvaliacaoFisica;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AvaliacaoFisicaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;
import repz.app.service.academia.AcademiaContextService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final AcademiaContextService academiaContextService;

    public AvaliacaoFisicaResponse criar(AvaliacaoFisicaCreateRequest request, Authentication auth) {
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

    public List<AvaliacaoFisicaResponse> findAll(Long alunoId, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.USUARIO && !currentUser.getId().equals(alunoId)) {
            throw new RuntimeException("USUARIO só pode ver suas próprias avaliações");
        }

        if (currentUser.getRole() == UserRole.PERSONAL) {
            personalRepository.findAll().stream()
                    .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Personal não encontrado"));
        }

        List<AvaliacaoFisica> avaliacoes = avaliacaoFisicaRepository.findByAluno_IdOrderByDataAvaliacaoDesc(alunoId);
        return avaliacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AvaliacaoFisicaResponse findById(Long id) {
        AvaliacaoFisica avaliacao = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        return toDTO(avaliacao);
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

    public List<AvaliacaoFisicaUnidadeResponse> obterDaUnidade(Long academiaHeaderId, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Long academiaId = academiaContextService.resolveOptional(auth, academiaHeaderId);

        if (currentUser.getRole() == UserRole.ADMIN) {
            return avaliacaoFisicaRepository.findAll().stream()
                    .filter(avaliacao -> academiaId == null
                            || avaliacao.getAcademia() != null && avaliacao.getAcademia().getId().equals(academiaId))
                    .map(this::toDTOUnidade)
                    .collect(Collectors.toList());
        } else if (currentUser.getRole() == UserRole.ACADEMIA) {
            return avaliacaoFisicaRepository.findByAcademia_Id(academiaId).stream()
                    .map(this::toDTOUnidade)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("Acesso negado");
    }

    public void ativar(Long id) {
        alterarStatus(id, true);
    }

    public void desativar(Long id) {
        alterarStatus(id, false);
    }

    private void alterarStatus(Long id, boolean ativo) {
        AvaliacaoFisica avaliacao = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacao.setAtivo(ativo);
        avaliacaoFisicaRepository.save(avaliacao);
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
