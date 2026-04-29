package repz.app.dto.request;

import java.math.BigDecimal;

public record PlanoPutRequest(
        String nome,
        Integer duracaoDias,
        BigDecimal valor
) {
}