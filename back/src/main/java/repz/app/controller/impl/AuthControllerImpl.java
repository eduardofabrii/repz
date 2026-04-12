package repz.app.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import repz.app.controller.AuthController;
import repz.app.dto.auth.AuthenticationDTO;
import repz.app.dto.auth.LoginResponseDTO;
import repz.app.persistence.entity.User;
import repz.app.persistence.repository.UserRepository;
import repz.app.service.security.TokenService;
import repz.app.service.user.UserService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) {
        var userPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(userPassword);
        var user = (User) Objects.requireNonNull(auth.getPrincipal());

        var token = tokenService.generateToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        userService.updateLastLogin(dto.email());
        return ResponseEntity.ok(new LoginResponseDTO(token, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody String refreshToken) {
        var email = tokenService.validateRefreshToken(refreshToken.trim());
        if (email == null) {
            return ResponseEntity.status(401).build();
        }
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        var user = userOptional.get();
        var newToken = tokenService.generateToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(newToken, newRefreshToken));
    }
}
