package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados para criação de personal")
public class PersonalCreateRequest {
    @Schema(description = "ID do usuário que será vinculado ao personal", example = "1")
    @NotNull(message = "User ID é obrigatório.")
    private Long userId;

    @Schema(description = "ID da academia vinculada", example = "1")
    @NotNull(message = "Academia ID é obrigatório.")
    private Long academiaId;

    @Schema(description = "Especialidade do personal", example = "Musculação")
    @NotBlank(message = "Especialidade é obrigatória.")
    private String especialidade;
}
