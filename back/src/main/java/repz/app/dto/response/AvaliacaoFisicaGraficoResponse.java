package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AvaliacaoFisicaGraficoResponse {
    private Long alunoId;

    private String alunoNome;

    private List<DadoGrafico> dados;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DadoGrafico {
        private LocalDateTime data;
        private Double peso;
        private Double imc;
        private Double percentualGordura;
    }
}

