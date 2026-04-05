package repz.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;


@Tag(name = "Auth", description = "Endpoints para controle de autorização e autenticação")
@RequestMapping("/auth")
public interface AuthController {
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto);
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader);
    ResponseEntity<LoginResponseDTO> refresh(@RequestBody String refreshToken);
}