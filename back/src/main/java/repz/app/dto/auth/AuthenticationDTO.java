package repz.app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação do usuário")
public record AuthenticationDTO(
        @Schema(description = "E-mail do usuário", example = "admin@repz.com")
        @NotBlank(message = "Informe o e-mail.")
        @Email(message = "E-mail inválido.")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "Informe a senha.")
        String password
) {
}
