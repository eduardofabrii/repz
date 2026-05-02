package repz.app.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.dto.response.PlanoResponse;

import java.util.List;

@RequestMapping("/api/planos")
@Tag(name = "Planos")
public interface PlanoController {

    @PostMapping
    ResponseEntity<Void> criar(@RequestBody @Valid PlanoPostRequest dto);

    @GetMapping
    ResponseEntity<List<PlanoResponse>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<PlanoResponse> findById(@PathVariable Integer id);

    @PutMapping("/{id}")
    ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid PlanoPutRequest dto);

    @PatchMapping("/{id}/ativar")
    ResponseEntity<Void> ativar(@PathVariable Integer id);

    @PatchMapping("/{id}/desativar")
    ResponseEntity<Void> desativar(@PathVariable Integer id);
}
