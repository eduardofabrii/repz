package repz.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import repz.app.dto.auth.RegistrationDTO;
import repz.app.message.Mensagens;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.UserRepository;
import repz.app.service.user.UserDetailsServiceImpl;
import repz.app.service.user.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static repz.app.unit.UnitTestData.auth;
import static repz.app.unit.UnitTestData.user;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Mensagens mensagens;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void criarSemTokenPermiteApenasUsuarioConvencional() {
        when(userRepository.findByEmail("aluno@repz.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hash");

        userService.criar(new RegistrationDTO("Aluno", "aluno@repz.com", "123456", null), null);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.USUARIO);
        assertThat(captor.getValue().getPassword()).isEqualTo("hash");
        assertThat(captor.getValue().getActive()).isTrue();
    }

    @Test
    void criarSemTokenRejeitaRolePrivilegiada() {
        when(userRepository.findByEmail("personal@repz.com")).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () ->
                userService.criar(
                        new RegistrationDTO("Personal", "personal@repz.com", "123456", UserRole.PERSONAL),
                        null));

        verify(userRepository, never()).save(any());
    }

    @Test
    void academiaPodeCriarUsuarioEPersonalMasNaoAcademia() {
        User academia = user(10L, UserRole.ACADEMIA);
        when(userRepository.findByEmail("personal@repz.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hash");

        userService.criar(
                new RegistrationDTO("Personal", "personal@repz.com", "123456", UserRole.PERSONAL),
                auth(academia));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.PERSONAL);

        when(userRepository.findByEmail("academia2@repz.com")).thenReturn(Optional.empty());
        assertThrows(AccessDeniedException.class, () ->
                userService.criar(
                        new RegistrationDTO("Academia 2", "academia2@repz.com", "123456", UserRole.ACADEMIA),
                        auth(academia)));
    }

    @Test
    void personalPodeCriarSomentePersonal() {
        User personal = user(20L, UserRole.PERSONAL);
        when(userRepository.findByEmail("aluno@repz.com")).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () ->
                userService.criar(
                        new RegistrationDTO("Aluno", "aluno@repz.com", "123456", UserRole.USUARIO),
                        auth(personal)));

        verify(userRepository, never()).save(any());
    }

    @Test
    void adminPodeCriarQualquerRole() {
        User admin = user(1L, UserRole.ADMIN);
        when(userRepository.findByEmail("academia@repz.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hash");

        userService.criar(
                new RegistrationDTO("Academia", "academia@repz.com", "123456", UserRole.ACADEMIA),
                auth(admin));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.ACADEMIA);
    }

    @Test
    void criarRejeitaEmailDuplicado() {
        when(userRepository.findByEmail("aluno@repz.com")).thenReturn(Optional.of(user(30L, UserRole.USUARIO)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                userService.criar(
                        new RegistrationDTO("Aluno", "aluno@repz.com", "123456", UserRole.USUARIO),
                        null));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(userRepository, never()).save(any());
    }

    @Test
    void loadUserByUsernameRetornaUsuarioOuErro() {
        User aluno = user(40L, UserRole.USUARIO);
        UserDetailsServiceImpl service = new UserDetailsServiceImpl(userRepository, mensagens);
        when(userRepository.findByEmail("aluno@repz.com")).thenReturn(Optional.of(aluno));

        assertThat(service.loadUserByUsername("aluno@repz.com")).isSameAs(aluno);

        when(userRepository.findByEmail("missing@repz.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.loadUserByUsername("missing@repz.com"))
                .isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
    }
}
