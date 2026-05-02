package repz.app.service.academia;

import repz.app.dto.request.AcademiaCreateRequest;
import repz.app.dto.request.AcademiaUpdateRequest;
import repz.app.dto.response.AcademiaDashboardResponse;
import repz.app.dto.response.AcademiaResponse;
import repz.app.persistence.entity.User;

import java.util.List;

public interface AcademiaService {

    AcademiaResponse criar(AcademiaCreateRequest dto);

    List<AcademiaResponse> findAll();

    AcademiaResponse findById(Long id);

    AcademiaResponse atualizar(Long id, AcademiaUpdateRequest dto);

    AcademiaResponse ativar(Long id);

    AcademiaResponse desativar(Long id);

    AcademiaResponse obterMinha(User currentUser);

    AcademiaResponse atualizarMinha(User currentUser, AcademiaUpdateRequest dto);

    AcademiaDashboardResponse obterDashboard();
}
