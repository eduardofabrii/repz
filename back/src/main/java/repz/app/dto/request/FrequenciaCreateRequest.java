package repz.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrequenciaCreateRequest {
    @NotNull(message = "Aluno ID é obrigatório")
    private Long alunoId;

    @NotNull(message = "Academia ID é obrigatório")
    private Long academiaId;

    private Long personalId;

    private LocalDateTime dataHora;
}

