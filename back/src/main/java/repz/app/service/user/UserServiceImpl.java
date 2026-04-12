package repz.app.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repz.app.dto.auth.RegistrationDTO;
import repz.app.dto.request.UserPutRequest;
import repz.app.dto.response.UserGetResponse;
import repz.app.persistence.entity.User;
import repz.app.persistence.mapper.UserMapper;
import repz.app.persistence.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserGetResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserGetResponse findUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return userMapper.toResponse(user);
    }

    @Override
    public void updateLastLogin(String email) {
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLastLogin(java.time.LocalDateTime.now());
            userRepository.save(user);
        }
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        if (userRepository.findByEmail(registrationDTO.email()).isPresent()) {
            throw new RuntimeException("Email já registrado");
        }

        User user = new User();
        user.setName(registrationDTO.name());
        user.setEmail(registrationDTO.email());
        user.setPassword(passwordEncoder.encode(registrationDTO.password()));

        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(Integer id, UserPutRequest userPutRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        user.setName(userPutRequest.name());
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void restoreUser(Integer id) {
        throw new UnsupportedOperationException("Não implementado ainda");
    }
}
