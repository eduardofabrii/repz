package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados para registro de check-in")
public class FrequenciaCreateRequest {
    @Schema(description = "ID do aluno", example = "1")
    @NotNull(message = "Aluno ID é obrigatório.")
    private Long alunoId;

    @Schema(description = "ID da academia", example = "1")
    @NotNull(message = "Academia ID é obrigatório.")
    private Long academiaId;

    @Schema(description = "ID do personal responsável pelo registro", example = "1")
    private Long personalId;

    @Schema(description = "Data e hora do check-in", example = "2026-05-02T15:30:00")
    private LocalDateTime dataHora;
}
