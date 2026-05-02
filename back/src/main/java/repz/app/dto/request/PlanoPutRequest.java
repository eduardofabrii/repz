package repz.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PlanoPutRequest(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotNull(message = "Duração em dias é obrigatória.")
        @Positive(message = "Duração em dias deve ser positiva.")
        Integer duracaoDias,

        @NotNull(message = "Valor é obrigatório.")
        @Positive(message = "Valor deve ser positivo.")
        BigDecimal valor
) {
}
