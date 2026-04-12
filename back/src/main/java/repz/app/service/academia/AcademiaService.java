package repz.app.service.academia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repz.app.dto.request.AcademiaCreateRequest;
import repz.app.dto.request.AcademiaUpdateRequest;
import repz.app.dto.response.AcademiaDashboardResponse;
import repz.app.dto.response.AcademiaResponse;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.User;
import repz.app.persistence.mapper.AcademiaMapper;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AcademiaService {

    private final AcademiaRepository academiaRepository;
    private final UserRepository userRepository;
    private final AcademiaMapper academiaMapper;

    public AcademiaResponse criar(AcademiaCreateRequest dto) {
        if (academiaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Academia com este CNPJ já existe");
        }

        Academia academia = academiaMapper.toEntity(dto);
        Academia saved = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<AcademiaResponse> listarTodas() {
        return academiaRepository.findAll().stream()
                .map(academiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AcademiaResponse> listarAativas() {
        return academiaRepository.findByActiveTrue().stream()
                .map(academiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AcademiaResponse obterPorId(Long id) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));
        return academiaMapper.toResponseDTO(academia);
    }

    public AcademiaResponse atualizar(Long id, AcademiaUpdateRequest dto) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));

        if (!academia.getCnpj().equals(dto.getCnpj()) &&
                academiaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Outro CNPJ já existe com esse valor");
        }

        academia.setCnpj(dto.getCnpj());
        academiaMapper.updateEntity(dto, academia);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    public AcademiaResponse inativar(Long id) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));

        academia.setActive(false);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    @Transactional(readOnly = true)
    public AcademiaResponse obterMinha(User currentUser) {
        List<Academia> academias = academiaRepository.findByResponsibleUserId(currentUser.getId());

        if (academias.isEmpty()) {
            throw new IllegalArgumentException("Usuário não possui academia associada");
        }

        return academiaMapper.toResponseDTO(academias.get(0));
    }

    public AcademiaResponse atualizarMinha(User currentUser, AcademiaUpdateRequest dto) {
        List<Academia> academias = academiaRepository.findByResponsibleUserId(currentUser.getId());

        if (academias.isEmpty()) {
            throw new IllegalArgumentException("Usuário não possui academia associada");
        }

        Academia academia = academias.get(0);

        if (!academia.getCnpj().equals(dto.getCnpj())) {
            throw new IllegalArgumentException("Não é permitido alterar o CNPJ da academia");
        }

        academiaMapper.updateEntity(dto, academia);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    @Transactional(readOnly = true)
    public AcademiaDashboardResponse obterDashboard() {
        List<Academia> allAcademias = academiaRepository.findAll();
        List<Academia> activeAcademias = academiaRepository.findByActiveTrue();

        long totalAcademies = allAcademias.size();
        int totalStudents = allAcademias.stream()
                .mapToInt(a -> a.getTotalStudents() != null ? a.getTotalStudents() : 0)
                .sum();
        int totalInstructors = allAcademias.stream()
                .mapToInt(a -> a.getTotalInstructors() != null ? a.getTotalInstructors() : 0)
                .sum();
        int totalActive = activeAcademias.size();
        int totalInactive = (int) (totalAcademies - totalActive);
        double averageStudents = totalAcademies > 0 ? (double) totalStudents / totalAcademies : 0;

        return new AcademiaDashboardResponse(
                totalAcademies,
                totalStudents,
                totalInstructors,
                totalActive,
                totalInactive,
                averageStudents
        );
    }
}
