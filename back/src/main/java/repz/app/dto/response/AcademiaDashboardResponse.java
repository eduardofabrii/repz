package repz.app.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Indicadores consolidados das academias")
public class AcademiaDashboardResponse {
    private Long totalAcademies;
    private Integer totalStudents;
    private Integer totalInstructors;
    private Integer totalActiveAcademies;
    private Integer totalInactiveAcademies;
    private Double averageStudentsPerAcademy;
}
