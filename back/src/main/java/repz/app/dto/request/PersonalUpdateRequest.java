package repz.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalUpdateRequest {
    @NotBlank(message = "Especialidade é obrigatória")
    private String especialidade;
    private Boolean ativo;
}

