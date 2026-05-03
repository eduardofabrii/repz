package repz.app.service;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

import java.util.UUID;

public abstract class ServiceIntegrationSupport {

    @MockitoBean
    MinioClient minioClient;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AcademiaRepository academiaRepository;

    @Autowired
    protected PersonalRepository personalRepository;

    protected User criarUsuario(UserRole role, String prefixo) {
        String unique = prefixo + "-" + UUID.randomUUID();

        User user = new User();
        user.setName("Usuário " + unique);
        user.setEmail(unique + "@repz.com");
        user.setPassword("{noop}123456");
        user.setRole(role);
        user.setActive(true);
        return userRepository.saveAndFlush(user);
    }

    protected Academia criarAcademia(User responsavel, String prefixo) {
        String cnpj = UUID.randomUUID().toString().replace("-", "").substring(0, 14);

        Academia academia = new Academia();
        academia.setCnpj(cnpj);
        academia.setName("Academia " + prefixo);
        academia.setAddress("Rua dos Testes, 100");
        academia.setResponsible("Responsável " + prefixo);
        academia.setResponsibleUser(responsavel);
        academia.setActive(true);
        academia.setTotalStudents(0);
        academia.setTotalInstructors(0);
        academia.setEmail(prefixo + "@repz.com");
        academia.setPhone("11999999999");
        return academiaRepository.saveAndFlush(academia);
    }

    protected Personal criarPersonal(User user, Academia academia) {
        Personal personal = new Personal();
        personal.setUser(user);
        personal.setAcademia(academia);
        personal.setEspecialidade("Musculação");
        personal.setAtivo(true);
        return personalRepository.saveAndFlush(personal);
    }

    protected Authentication autenticar(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    protected void logar(User user) {
        SecurityContextHolder.getContext().setAuthentication(autenticar(user));
    }

    protected void deslogar() {
        SecurityContextHolder.clearContext();
    }
}
