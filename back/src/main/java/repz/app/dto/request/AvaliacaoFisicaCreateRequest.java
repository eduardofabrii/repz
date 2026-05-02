package repz.app.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoFisicaCreateRequest {
    @NotNull(message = "Aluno ID é obrigatório.")
    private Long alunoId;

    @NotNull(message = "Peso é obrigatório.")
    @Positive(message = "Peso deve ser positivo.")
    private Double pesoKg;

    @NotNull(message = "Altura é obrigatória.")
    @Positive(message = "Altura deve ser positiva.")
    private Double alturaCm;

    private Double percentualGordura;

    private String medidas;
}
