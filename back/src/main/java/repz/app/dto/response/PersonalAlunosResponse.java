package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "Alunos vinculados a um personal")
public class PersonalAlunosResponse {
    private Long personalId;
    private String personalNome;
    private String especialidade;
    private Long academiaId;
    private String academiaNome;
    private List<AlunoResponse> alunos;
}
