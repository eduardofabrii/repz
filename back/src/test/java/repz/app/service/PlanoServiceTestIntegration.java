package repz.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.persistence.entity.UserRole;
import repz.app.service.plano.PlanoService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PlanoServiceTestIntegration extends ServiceIntegrationSupport {

    @Autowired
    private PlanoService planoService;

    @AfterEach
    void clearSecurityContext() {
        deslogar();
    }

    @Test
    void criaListaBuscaAtualizaEAlteraStatusDoPlanoDaAcademiaLogada() {
        var academia = criarUsuario(UserRole.ACADEMIA, "academia-plano");
        logar(academia);

        planoService.criar(new PlanoPostRequest("Mensal", 30, new BigDecimal("99.90")));

        var planos = planoService.findAll();
        assertThat(planos).hasSize(1);
        var plano = planos.getFirst();
        assertThat(plano.nome()).isEqualTo("Mensal");
        assertThat(plano.ativo()).isTrue();

        planoService.atualizar(plano.id(), new PlanoPutRequest("Trimestral", 90, new BigDecimal("249.90")));
        assertThat(planoService.findById(plano.id()).nome()).isEqualTo("Trimestral");

        planoService.desativar(plano.id());
        assertThat(planoService.findById(plano.id()).ativo()).isFalse();

        planoService.ativar(plano.id());
        assertThat(planoService.findById(plano.id()).ativo()).isTrue();
    }

    @Test
    void planoFicaIsoladoPorAcademiaLogada() {
        var academiaA = criarUsuario(UserRole.ACADEMIA, "academia-plano-a");
        var academiaB = criarUsuario(UserRole.ACADEMIA, "academia-plano-b");

        logar(academiaA);
        planoService.criar(new PlanoPostRequest("Plano A", 30, new BigDecimal("99.90")));
        var planoAId = planoService.findAll().getFirst().id();

        logar(academiaB);
        assertThat(planoService.findAll()).isEmpty();
        assertThatThrownBy(() -> planoService.findById(planoAId))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }
}
