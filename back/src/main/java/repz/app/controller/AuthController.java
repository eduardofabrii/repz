package repz.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;

@Tag(name = "Auth", description = "Endpoints para controle de autorização e autenticação")
@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader);

    @PostMapping("/refresh")
    ResponseEntity<LoginResponseDTO> refresh(@RequestBody String refreshToken);

    
}

