package repz.app.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInCreateRequest {
    private Long alunoId;
    private Long academiaId;
    private Long personalId;
    private LocalDateTime dataHora;
}
