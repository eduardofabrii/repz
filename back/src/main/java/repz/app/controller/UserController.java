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
import repz.app.dto.auth.RegistrationDTO;
import repz.app.dto.request.UserPutRequest;
import repz.app.dto.response.UserGetResponse;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    ResponseEntity<List<UserGetResponse>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<UserGetResponse> findById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<Void> criar(@RequestBody @Valid RegistrationDTO registrationDTO);

    @PutMapping("/{id}")
    ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid UserPutRequest userPutRequest);

    @PatchMapping("/{id}/ativar")
    ResponseEntity<Void> ativar(@PathVariable Integer id);

    @PatchMapping("/{id}/desativar")
    ResponseEntity<Void> desativar(@PathVariable Integer id);
}
