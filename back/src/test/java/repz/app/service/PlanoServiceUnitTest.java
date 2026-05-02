package repz.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import repz.app.dto.request.PlanoPostRequest;
import repz.app.dto.request.PlanoPutRequest;
import repz.app.persistence.entity.Plano;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.PlanoRepository;
import repz.app.service.plano.PlanoServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static repz.app.unit.UnitTestData.auth;
import static repz.app.unit.UnitTestData.user;

@ExtendWith(MockitoExtension.class)
class PlanoServiceUnitTest {

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private PlanoServiceImpl service;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void criarPlanoSempreVinculaNaAcademiaLogada() {
        User academia = user(1L, UserRole.ACADEMIA);
        SecurityContextHolder.getContext().setAuthentication(auth(academia));

        service.criar(new PlanoPostRequest("Mensal", 30, BigDecimal.valueOf(99.90)));

        ArgumentCaptor<Plano> captor = ArgumentCaptor.forClass(Plano.class);
        verify(planoRepository).save(captor.capture());
        assertThat(captor.getValue().getAcademia()).isSameAs(academia);
        assertThat(captor.getValue().getAtivo()).isTrue();
    }

    @Test
    void atualizarPlanoRespeitaEscopoDaAcademiaLogada() {
        User academia = user(1L, UserRole.ACADEMIA);
        Plano plano = Plano.builder()
                .id(5)
                .nome("Mensal")
                .duracaoDias(30)
                .valor(BigDecimal.valueOf(99.90))
                .ativo(true)
                .academia(academia)
                .build();
        SecurityContextHolder.getContext().setAuthentication(auth(academia));
        when(planoRepository.findByIdAndAcademia(5, academia)).thenReturn(Optional.of(plano));

        service.atualizar(5, new PlanoPutRequest("Trimestral", 90, BigDecimal.valueOf(249.90)));

        assertThat(plano.getNome()).isEqualTo("Trimestral");
        assertThat(plano.getDuracaoDias()).isEqualTo(90);
        assertThat(plano.getValor()).isEqualByComparingTo("249.90");
        verify(planoRepository).save(plano);
    }
}
