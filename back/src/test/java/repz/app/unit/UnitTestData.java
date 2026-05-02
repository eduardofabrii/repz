package repz.app.unit;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.AvaliacaoFisica;
import repz.app.persistence.entity.Frequencia;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;

import java.time.LocalDateTime;

public final class UnitTestData {

    private UnitTestData() {
    }

    public static User user(Long id, UserRole role) {
        User user = new User();
        user.setId(id);
        user.setName(role.name() + " " + id);
        user.setEmail(role.name().toLowerCase() + id + "@repz.com");
        user.setPassword("encoded");
        user.setRole(role);
        user.setActive(true);
        return user;
    }

    public static Academia academia(Long id, User responsibleUser) {
        Academia academia = new Academia();
        academia.setId(id);
        academia.setName("Academia " + id);
        academia.setCnpj(String.format("%014d", id));
        academia.setAddress("Rua " + id);
        academia.setResponsible("Responsavel " + id);
        academia.setResponsibleUser(responsibleUser);
        academia.setActive(true);
        return academia;
    }

    public static Personal personal(Long id, User user, Academia academia) {
        Personal personal = new Personal();
        personal.setId(id);
        personal.setUser(user);
        personal.setAcademia(academia);
        personal.setEspecialidade("Musculacao");
        personal.setAtivo(true);
        return personal;
    }

    public static Frequencia frequencia(Long id, User aluno, Academia academia, Personal personal, LocalDateTime dataHora) {
        Frequencia frequencia = new Frequencia();
        frequencia.setId(id);
        frequencia.setAluno(aluno);
        frequencia.setAcademia(academia);
        frequencia.setRegistradoPor(personal);
        frequencia.setDataHora(dataHora);
        frequencia.setAtivo(true);
        return frequencia;
    }

    public static AvaliacaoFisica avaliacao(Long id, User aluno, Academia academia, Personal personal) {
        AvaliacaoFisica avaliacao = new AvaliacaoFisica();
        avaliacao.setId(id);
        avaliacao.setAluno(aluno);
        avaliacao.setAcademia(academia);
        avaliacao.setPersonal(personal);
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setPesoKg(80.0);
        avaliacao.setAlturaCm(180.0);
        avaliacao.setImc(24.69);
        avaliacao.setPercentualGordura(18.0);
        avaliacao.setMedidas("Cintura: 82cm");
        avaliacao.setAtivo(true);
        return avaliacao;
    }

    public static Authentication auth(String email) {
        return new UsernamePasswordAuthenticationToken(email, null);
    }

    public static Authentication auth(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
