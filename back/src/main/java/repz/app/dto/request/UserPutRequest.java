package repz.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import repz.app.persistence.entity.UserRole;

public record UserPutRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        UserRole role,

        Boolean active
) {
}