package repz.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.dto.response.PlanoResponse;
import repz.app.service.plano.PlanoService;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
@Tag(name = "Planos")
public class PlanoController {

    private final PlanoService planoService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody PlanoPostRequest dto) {
        planoService.criar(dto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<PlanoResponse>> listar() {
        return ResponseEntity.ok(planoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(
            @PathVariable Integer id,
            @RequestBody PlanoPutRequest dto) {

        planoService.editar(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        planoService.inativar(id);
        return ResponseEntity.ok().build();
    }
}