package repz.app.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.AuthController;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;
import repz.app.message.Mensagens;
import repz.app.persistence.entity.User;
import repz.app.persistence.repository.UserRepository;
import repz.app.service.security.TokenService;
import repz.app.service.user.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final Mensagens mensagens;

    @Override
    public ResponseEntity<LoginResponseDTO> login(AuthenticationDTO dto) {
        var userPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(userPassword);
        var user = (User) Objects.requireNonNull(auth.getPrincipal());

        var token = tokenService.generateToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        userService.updateLastLogin(dto.email());
        return ResponseEntity.ok(new LoginResponseDTO(token, refreshToken));
    }

    @Override
    public ResponseEntity<Void> logout(String authHeader) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LoginResponseDTO> refresh(String refreshToken) {
        var email = tokenService.validateRefreshToken(refreshToken.trim());
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, mensagens.get("erro.autenticacao"));
        }
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, mensagens.get("erro.autenticacao"));
        }
        var user = userOptional.get();
        var newToken = tokenService.generateToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(newToken, newRefreshToken));
    }
}
