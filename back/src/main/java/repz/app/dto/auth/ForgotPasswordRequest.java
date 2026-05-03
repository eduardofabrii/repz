package repz.app.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "E-mail do usuário para envio do link de recuperação")
public record ForgotPasswordRequest(

        @Schema(description = "E-mail cadastrado", example = "usuario@repz.com")
        @NotBlank(message = "Insira o e-mail.")
        @Email(message = "E-mail inválido.")
        String email
) {
}
