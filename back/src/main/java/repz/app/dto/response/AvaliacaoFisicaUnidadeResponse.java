package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AvaliacaoFisicaUnidadeResponse {
    private Long id;

    private Long alunoId;

    private String alunoNome;

    private String alunoEmail;

    private Long personalId;

    private String personalNome;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAvaliacao;

    private Double pesoKg;

    private Double alturaCm;

    private Double imc;

    private Double percentualGordura;
}

