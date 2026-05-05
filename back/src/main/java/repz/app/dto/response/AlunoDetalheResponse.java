package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "Perfil completo do aluno")
public class AlunoDetalheResponse {
    private Long id;
    private Long userId;
    private String nome;
    private String email;
    private String telefone;
    private String fotoUrl;
    private Long academiaId;
    private String academiaNome;
    private Long personalId;
    private String personalNome;
    private Integer planoId;
    private String planoNome;
    private String objetivo;
    private Boolean ativo;
}
