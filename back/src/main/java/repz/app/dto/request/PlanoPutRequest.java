package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Dados para atualização de plano")
public record PlanoPutRequest(
        @Schema(description = "Nome do plano", example = "Trimestral")
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @Schema(description = "Duração do plano em dias", example = "90")
        @NotNull(message = "Duração em dias é obrigatória.")
        @Positive(message = "Duração em dias deve ser positiva.")
        Integer duracaoDias,

        @Schema(description = "Valor do plano", example = "249.90")
        @NotNull(message = "Valor é obrigatório.")
        @Positive(message = "Valor deve ser positivo.")
        BigDecimal valor
) {
}
