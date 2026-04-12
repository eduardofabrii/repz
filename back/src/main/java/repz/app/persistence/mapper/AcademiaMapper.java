package repz.app.persistence.mapper;

import org.springframework.stereotype.Component;
import repz.app.dto.request.AcademiaCreateRequest;
import repz.app.dto.request.AcademiaUpdateRequest;
import repz.app.dto.response.AcademiaResponse;
import repz.app.persistence.entity.Academia;

@Component
public class AcademiaMapper {

    public AcademiaResponse toResponseDTO(Academia academia) {
        if (academia == null) {
            return null;
        }

        return new AcademiaResponse(
                academia.getId(),
                academia.getCnpj(),
                academia.getName(),
                academia.getAddress(),
                academia.getResponsible(),
                academia.getPhone(),
                academia.getEmail(),
                academia.getActive(),
                academia.getTotalStudents(),
                academia.getTotalInstructors(),
                academia.getDtInclusao(),
                academia.getDtAlteracao()
        );
    }

    public Academia toEntity(AcademiaCreateRequest dto) {
        if (dto == null) {
            return null;
        }

        Academia academia = new Academia();
        academia.setCnpj(dto.getCnpj());
        academia.setName(dto.getName());
        academia.setAddress(dto.getAddress());
        academia.setResponsible(dto.getResponsible());
        academia.setEmail(dto.getEmail());
        academia.setPhone(dto.getPhone());
        academia.setActive(true);
        academia.setTotalStudents(0);
        academia.setTotalInstructors(0);

        return academia;
    }

    public void updateEntity(AcademiaUpdateRequest dto, Academia academia) {
        if (dto == null || academia == null) {
            return;
        }

        academia.setName(dto.getName());
        academia.setAddress(dto.getAddress());
        academia.setResponsible(dto.getResponsible());
        academia.setEmail(dto.getEmail());
        academia.setPhone(dto.getPhone());
    }
}
