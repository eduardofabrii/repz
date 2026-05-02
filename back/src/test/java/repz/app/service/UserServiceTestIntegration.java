package repz.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import repz.app.dto.auth.RegistrationDTO;
import repz.app.dto.request.UserPutRequest;
import repz.app.persistence.entity.UserRole;
import repz.app.service.user.UserDetailsServiceImpl;
import repz.app.service.user.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTestIntegration extends ServiceIntegrationSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void criarSemTokenPermiteSomenteUsuarioConvencional() {
        var dto = new RegistrationDTO("Aluno Teste", "aluno-sem-token@repz.com", "123456", null);

        userService.criar(dto, null);

        var saved = userRepository.findByEmail("aluno-sem-token@repz.com").orElseThrow();
        assertThat(saved.getRole()).isEqualTo(UserRole.USUARIO);
        assertThat(saved.getActive()).isTrue();
        assertThat(saved.getPassword()).isNotEqualTo("123456");
    }

    @Test
    void criarSemTokenRejeitaRolePrivilegiada() {
        var dto = new RegistrationDTO("Admin Teste", "admin-sem-token@repz.com", "123456", UserRole.ADMIN);

        assertThatThrownBy(() -> userService.criar(dto, null))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void criarComAcademiaPermiteUsuarioEPersonal() {
        var academiaUser = criarUsuario(UserRole.ACADEMIA, "academia-criadora");
        var dto = new RegistrationDTO("Personal Novo", "personal-novo@repz.com", "123456", UserRole.PERSONAL);

        userService.criar(dto, autenticar(academiaUser));

        assertThat(userRepository.findByEmail("personal-novo@repz.com").orElseThrow().getRole())
                .isEqualTo(UserRole.PERSONAL);
    }

    @Test
    void criarComPersonalRejeitaUsuario() {
        var personalUser = criarUsuario(UserRole.PERSONAL, "personal-criador");
        var dto = new RegistrationDTO("Aluno Novo", "aluno-por-personal@repz.com", "123456", UserRole.USUARIO);

        assertThatThrownBy(() -> userService.criar(dto, autenticar(personalUser)))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void atualizarDesativarEAtivarUsuario() {
        var user = criarUsuario(UserRole.USUARIO, "usuario-status");

        userService.atualizar(Math.toIntExact(user.getId()), new UserPutRequest(
                "Nome Atualizado",
                "nao-usado@repz.com",
                UserRole.ADMIN,
                false));
        userService.desativar(Math.toIntExact(user.getId()));

        var desativado = userRepository.findById(Math.toIntExact(user.getId())).orElseThrow();
        assertThat(desativado.getName()).isEqualTo("Nome Atualizado");
        assertThat(desativado.getActive()).isFalse();
        assertThat(desativado.getDeletedAt()).isNotNull();

        userService.ativar(Math.toIntExact(user.getId()));

        var reativado = userRepository.findById(Math.toIntExact(user.getId())).orElseThrow();
        assertThat(reativado.getActive()).isTrue();
        assertThat(reativado.getDeletedAt()).isNull();
    }

    @Test
    void findByIdIgnoraUsuarioDeletado() {
        var user = criarUsuario(UserRole.USUARIO, "usuario-deletado");
        userService.desativar(Math.toIntExact(user.getId()));

        assertThatThrownBy(() -> userService.findById(Math.toIntExact(user.getId())))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void userDetailsCarregaPorEmailERejeitaInexistente() {
        var user = criarUsuario(UserRole.USUARIO, "userdetails");

        assertThat(userDetailsService.loadUserByUsername(user.getEmail()).getUsername())
                .isEqualTo(user.getEmail());
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("ausente@repz.com"))
                .isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
    }
}
