package repz.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import repz.app.persistence.entity.UserRole;

public record UserPutRequest(
        @NotBlank(message = "Insira o nome.")
        String name,

        @NotBlank(message = "Insira o e-mail.")
        @Email(message = "E-mail inválido.")
        String email,

        UserRole role,

        Boolean active
) {
}
