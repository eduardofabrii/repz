package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.PlanoController;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.dto.response.PlanoResponse;
import repz.app.service.plano.PlanoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanoControllerImpl implements PlanoController {

    private final PlanoService planoService;

    @Override
    public ResponseEntity<Void> criar(PlanoPostRequest dto) {
        planoService.criar(dto);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<List<PlanoResponse>> findAll() {
        return ResponseEntity.ok(planoService.findAll());
    }

    @Override
    public ResponseEntity<PlanoResponse> findById(Integer id) {
        return ResponseEntity.ok(planoService.findById(id));
    }

    @Override
    public ResponseEntity<Void> atualizar(Integer id, PlanoPutRequest dto) {
        planoService.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> ativar(Integer id) {
        planoService.ativar(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> desativar(Integer id) {
        planoService.desativar(id);
        return ResponseEntity.ok().build();
    }
}
