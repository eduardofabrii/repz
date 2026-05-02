package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados para criação de avaliação física")
public class AvaliacaoFisicaCreateRequest {
    @Schema(description = "ID do aluno avaliado", example = "1")
    @NotNull(message = "Aluno ID é obrigatório.")
    private Long alunoId;

    @Schema(description = "Peso em quilogramas", example = "78.5")
    @NotNull(message = "Peso é obrigatório.")
    @Positive(message = "Peso deve ser positivo.")
    private Double pesoKg;

    @Schema(description = "Altura em centímetros", example = "175.0")
    @NotNull(message = "Altura é obrigatória.")
    @Positive(message = "Altura deve ser positiva.")
    private Double alturaCm;

    @Schema(description = "Percentual de gordura corporal", example = "18.5")
    private Double percentualGordura;

    @Schema(description = "Medidas corporais em texto livre", example = "Cintura: 82cm; Braço: 36cm")
    private String medidas;
}
