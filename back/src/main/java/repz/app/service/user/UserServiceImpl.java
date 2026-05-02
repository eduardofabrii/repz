package repz.app.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import repz.app.dto.auth.RegistrationDTO;
import repz.app.dto.request.UserPutRequest;
import repz.app.dto.response.UserGetResponse;
import repz.app.message.Mensagens;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.mapper.UserMapper;
import repz.app.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final Mensagens mensagens;

    @Override
    public List<UserGetResponse> findAll() {
        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserGetResponse findById(Integer id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                mensagens.get("usuario.nao.encontrado")));

        return userMapper.toResponse(user);
    }

    @Override
    public void updateLastLogin(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    @Override
    public void criar(RegistrationDTO dto, Authentication authentication) {

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    mensagens.get("usuario.email.ja.cadastrado"));
        }

        UserRole role = resolveRoleForCreation(dto.role(), authentication);

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(role);
        user.setActive(true);

        userRepository.save(user);
    }

    private UserRole resolveRoleForCreation(UserRole requestedRole, Authentication authentication) {
        UserRole role = requestedRole != null ? requestedRole : UserRole.USUARIO;

        if (authentication == null || !(authentication.getPrincipal() instanceof User currentUser)) {
            if (role == UserRole.USUARIO) {
                return UserRole.USUARIO;
            }
            throw new AccessDeniedException(mensagens.get("usuario.criacao.autenticado.obrigatorio", role));
        }

        UserRole currentRole = currentUser.getRole();
        if (currentRole == UserRole.ADMIN) {
            return role;
        }

        if (currentRole == UserRole.USUARIO && role == UserRole.USUARIO) {
            return UserRole.USUARIO;
        }

        if (currentRole == UserRole.ACADEMIA
                && (role == UserRole.PERSONAL || role == UserRole.USUARIO)) {
            return role;
        }

        if (currentRole == UserRole.PERSONAL && role == UserRole.PERSONAL) {
            return UserRole.PERSONAL;
        }

        throw new AccessDeniedException(mensagens.get("usuario.criacao.role.negada", role));
    }

    @Override
    public void atualizar(Integer id, UserPutRequest dto) {

        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                mensagens.get("usuario.nao.encontrado")));

        user.setName(dto.name());

        userRepository.save(user);
    }

    @Override
    public void desativar(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                mensagens.get("usuario.nao.encontrado")));

        user.setActive(false);
        user.setDeletedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public void ativar(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                mensagens.get("usuario.nao.encontrado")));

        user.setActive(true);
        user.setDeletedAt(null);

        userRepository.save(user);
    }
}
