package repz.app.dto.response;

import java.math.BigDecimal;

public record PlanoResponse(
        Integer id,
        String nome,
        Integer duracaoDias,
        BigDecimal valor,
        Boolean ativo
) {
}