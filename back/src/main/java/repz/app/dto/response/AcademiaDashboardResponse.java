package repz.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademiaDashboardResponse {
    private Long totalAcademies;
    private Integer totalStudents;
    private Integer totalInstructors;
    private Integer totalActiveAcademies;
    private Integer totalInactiveAcademies;
    private Double averageStudentsPerAcademy;
}

