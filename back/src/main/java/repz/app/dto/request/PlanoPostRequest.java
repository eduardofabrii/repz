package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Dados para criação de plano")
public record PlanoPostRequest(
        @Schema(description = "Nome do plano", example = "Mensal")
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @Schema(description = "Duração do plano em dias", example = "30")
        @NotNull(message = "Duração em dias é obrigatória.")
        @Positive(message = "Duração em dias deve ser positiva.")
        Integer duracaoDias,

        @Schema(description = "Valor do plano", example = "99.90")
        @NotNull(message = "Valor é obrigatório.")
        @Positive(message = "Valor deve ser positivo.")
        BigDecimal valor
) {
}
