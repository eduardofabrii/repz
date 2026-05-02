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

@Service
@RequiredArgsConstructor
public class PlanoServiceImpl implements PlanoService {

    private final PlanoRepository planoRepository;

    private User usuarioLogado() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
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
    public List<PlanoResponse> listar() {

        User academia = usuarioLogado();

        return planoRepository.findByAcademia(academia)
                .stream()
                .map(p -> new PlanoResponse(
                        p.getId(),
                        p.getNome(),
                        p.getDuracaoDias(),
                        p.getValor(),
                        p.getAtivo()))
                .toList();
    }

    @Override
    public void editar(Integer id, PlanoPutRequest dto) {

        User academia = usuarioLogado();

        Plano plano = planoRepository.findByIdAndAcademia(id, academia)
                .orElseThrow();

        plano.setNome(dto.nome());
        plano.setDuracaoDias(dto.duracaoDias());
        plano.setValor(dto.valor());

        planoRepository.save(plano);
    }

    @Override
    public void inativar(Integer id) {

        User academia = usuarioLogado();

        Plano plano = planoRepository.findByIdAndAcademia(id, academia)
                .orElseThrow();

        plano.setAtivo(false);

        planoRepository.save(plano);
    }
}