package repz.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
        @NotBlank(message = "Insira o nome")
        String name,

        @NotBlank(message = "Insira o e-mail.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "Insira a senha.")
        @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
        String password
) {
}
