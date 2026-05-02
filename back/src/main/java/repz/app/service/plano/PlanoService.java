package repz.app.service.plano;

import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.dto.response.PlanoResponse;

import java.util.List;

public interface PlanoService {

    void criar(PlanoPostRequest dto);

    List<PlanoResponse> findAll();

    PlanoResponse findById(Integer id);

    void atualizar(Integer id, PlanoPutRequest dto);

    void ativar(Integer id);

    void desativar(Integer id);
}
