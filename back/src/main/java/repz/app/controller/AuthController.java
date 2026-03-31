package repz.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;

public interface AuthController {
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto);
}