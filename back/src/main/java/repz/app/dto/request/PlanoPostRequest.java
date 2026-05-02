package repz.app.dto.request;

import java.math.BigDecimal;

public record PlanoPostRequest(
        String nome,
        Integer duracaoDias,
        BigDecimal valor
) {
}