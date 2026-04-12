package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class FrequenciaRelatorioResponse {
    private Long academiaId;

    private Map<String, LocalDateTime> periodo;

    private Long totalFrequencias;

    private Map<String, Long> frequenciaPorAluno;
}

