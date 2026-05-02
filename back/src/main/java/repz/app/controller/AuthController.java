package repz.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;

@Tag(name = "Autenticação", description = "Login, logout e renovação de token")
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/login")
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna tokens de acesso e renovação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto);

    @PostMapping("/logout")
    @Operation(summary = "Realizar logout", description = "Encerra a sessão atual do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    ResponseEntity<Void> logout(
            @Parameter(description = "Token JWT no formato Bearer")
            @RequestHeader(value = "Authorization", required = false) String authHeader);

    @PostMapping("/refresh")
    @Operation(
            summary = "Renovar token",
            description = "Gera um novo par de tokens a partir de um refresh token válido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido")
    })
    ResponseEntity<LoginResponseDTO> refresh(@RequestBody String refreshToken);
}
