package repz.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademiaCreateRequest {

    @NotBlank(message = "CNPJ é obrigatório.")
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve conter 14 dígitos.")
    private String cnpj;

    @NotBlank(message = "Nome é obrigatório.")
    private String name;

    @NotBlank(message = "Endereço é obrigatório.")
    private String address;

    @NotBlank(message = "Responsável é obrigatório.")
    private String responsible;

    @Email(message = "Email deve ser válido.")
    private String email;

    private String phone;
}
