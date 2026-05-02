package repz.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados de plano")
public record PlanoResponse(
        @Schema(description = "ID do plano", example = "1")
        Integer id,

        @Schema(description = "Nome do plano", example = "Mensal")
        String nome,

        @Schema(description = "Duração em dias", example = "30")
        Integer duracaoDias,

        @Schema(description = "Valor do plano", example = "99.90")
        BigDecimal valor,

        @Schema(description = "Status ativo do plano", example = "true")
        Boolean ativo
) {
}
