package repz.app.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CheckInResponse {
    private Long id;
    private LocalDateTime dataHora;
    private Long alunoId;
    private String alunoNome;
    private Long academiaId;
    private String academiaNome;
    private Long registradoPorId;
    private String registradoPorNome;
}
