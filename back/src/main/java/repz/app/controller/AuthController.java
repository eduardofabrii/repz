package repz.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.validation.Valid;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;

public interface AuthController {
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto);
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader);
    ResponseEntity<LoginResponseDTO> refresh(@RequestBody String refreshToken);
}