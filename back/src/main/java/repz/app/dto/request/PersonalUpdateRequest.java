package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados para atualização de personal")
public class PersonalUpdateRequest {
    @Schema(description = "Especialidade do personal", example = "Funcional")
    @NotBlank(message = "Especialidade é obrigatória.")
    private String especialidade;

    @Schema(description = "Status ativo do personal", example = "true")
    private Boolean ativo;
}
