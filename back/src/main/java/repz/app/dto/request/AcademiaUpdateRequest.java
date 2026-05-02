package repz.app.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualização de academia")
public class AcademiaUpdateRequest {

    @Schema(description = "CNPJ da academia com 14 dígitos", example = "12345678000199")
    @NotBlank(message = "CNPJ é obrigatório.")
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve conter 14 dígitos.")
    private String cnpj;

    @Schema(description = "Nome da academia", example = "Repz Fitness")
    @NotBlank(message = "Nome é obrigatório.")
    private String name;

    @Schema(description = "Endereço da academia", example = "Rua Central, 100")
    @NotBlank(message = "Endereço é obrigatório.")
    private String address;

    @Schema(description = "Nome do responsável", example = "Eduardo Fabri")
    @NotBlank(message = "Responsável é obrigatório.")
    private String responsible;

    @Schema(description = "E-mail de contato", example = "contato@repz.com")
    @Email(message = "Email deve ser válido.")
    private String email;

    @Schema(description = "Telefone de contato", example = "11999999999")
    private String phone;
}
