package repz.app.service.plano;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.dto.response.PlanoResponse;
import repz.app.persistence.entity.Plano;
import repz.app.persistence.entity.User;
import repz.app.persistence.repository.PlanoRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlanoServiceImpl implements PlanoService {

    private final PlanoRepository planoRepository;

    private User usuarioLogado() {
        return (User) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();
    }

    @Override
    public void criar(PlanoPostRequest dto) {

        User academia = usuarioLogado();

        Plano plano = Plano.builder()
                .nome(dto.nome())
                .duracaoDias(dto.duracaoDias())
                .valor(dto.valor())
                .ativo(true)
                .academia(academia)
                .build();

        planoRepository.save(plano);
    }

    @Override
    public List<PlanoResponse> findAll() {

        User academia = usuarioLogado();

        return planoRepository.findByAcademia(academia)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PlanoResponse findById(Integer id) {
        User academia = usuarioLogado();

        Plano plano = planoRepository.findByIdAndAcademia(id, academia)
                .orElseThrow();

        return toResponse(plano);
    }

    @Override
    public void atualizar(Integer id, PlanoPutRequest dto) {

        User academia = usuarioLogado();

        Plano plano = planoRepository.findByIdAndAcademia(id, academia)
                .orElseThrow();

        plano.setNome(dto.nome());
        plano.setDuracaoDias(dto.duracaoDias());
        plano.setValor(dto.valor());

        planoRepository.save(plano);
    }

    @Override
    public void ativar(Integer id) {
        alterarStatus(id, true);
    }

    @Override
    public void desativar(Integer id) {
        alterarStatus(id, false);
    }

    private void alterarStatus(Integer id, boolean ativo) {

        User academia = usuarioLogado();

        Plano plano = planoRepository.findByIdAndAcademia(id, academia)
                .orElseThrow();

        plano.setAtivo(ativo);

        planoRepository.save(plano);
    }

    private PlanoResponse toResponse(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getDuracaoDias(),
                plano.getValor(),
                plano.getAtivo());
    }
}
