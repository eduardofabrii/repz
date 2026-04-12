package repz.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalCreateRequest {
    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    @NotNull(message = "Academia ID é obrigatório")
    private Long academiaId;

    @NotBlank(message = "Especialidade é obrigatória")
    private String especialidade;
}

