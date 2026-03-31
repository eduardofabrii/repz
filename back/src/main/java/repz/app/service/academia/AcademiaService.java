package repz.app.service.academia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repz.app.dto.academia.AcademiaDashboardDTO;
import repz.app.dto.academia.AcademiaRequestDTO;
import repz.app.dto.academia.AcademiaResponseDTO;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.User;
import repz.app.persistence.mapper.AcademiaMapper;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class AcademiaService {

    private final AcademiaRepository academiaRepository;
    private final UserRepository userRepository;
    private final AcademiaMapper academiaMapper;

    /**
     * RF06: Cadastrar Academia (apenas ADMIN)
     */
    public AcademiaResponseDTO createAcademia(AcademiaRequestDTO dto) {
        if (academiaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Academia com este CNPJ já existe");
        }

        Academia academia = academiaMapper.toEntity(dto);
        Academia saved = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(saved);
    }

    /**
     * RF07: Listar todas as academias (apenas ADMIN)
     */
    @Transactional(readOnly = true)
    public List<AcademiaResponseDTO> listAllAcademias() {
        return academiaRepository.findAll().stream()
                .map(academiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * RF07: Listar academias ativas (apenas ADMIN)
     */
    @Transactional(readOnly = true)
    public List<AcademiaResponseDTO> listActiveAcademias() {
        return academiaRepository.findByActiveTrue().stream()
                .map(academiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * RF07: Buscar academia por ID (apenas ADMIN)
     */
    @Transactional(readOnly = true)
    public AcademiaResponseDTO getAcademiaById(Long id) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));
        return academiaMapper.toResponseDTO(academia);
    }

    /**
     * RF07: Editar academia (apenas ADMIN)
     */
    public AcademiaResponseDTO updateAcademia(Long id, AcademiaRequestDTO dto) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));

        // Verificar se está tentando mudar o CNPJ para um que já existe
        if (!academia.getCnpj().equals(dto.getCnpj()) && 
            academiaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Outro CNPJ já existe com esse valor");
        }

        academia.setCnpj(dto.getCnpj());
        academiaMapper.updateEntity(dto, academia);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    /**
     * RF07: Inativar academia (apenas ADMIN) - soft delete via PATCH
     */
    public AcademiaResponseDTO deactivateAcademia(Long id) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Academia não encontrada"));

        academia.setActive(false);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    /**
     * RF08: Visualizar dados da própria academia (perfil ACADEMIA/GYM)
     */
    @Transactional(readOnly = true)
    public AcademiaResponseDTO getOwnAcademia(User currentUser) {
        List<Academia> academias = academiaRepository.findByResponsibleUserId(currentUser.getId());
        
        if (academias.isEmpty()) {
            throw new IllegalArgumentException("Usuário não possui academia associada");
        }

        return academiaMapper.toResponseDTO(academias.get(0));
    }

    /**
     * RF08: Editar própria academia (perfil ACADEMIA/GYM)
     */
    public AcademiaResponseDTO updateOwnAcademia(User currentUser, AcademiaRequestDTO dto) {
        List<Academia> academias = academiaRepository.findByResponsibleUserId(currentUser.getId());
        
        if (academias.isEmpty()) {
            throw new IllegalArgumentException("Usuário não possui academia associada");
        }

        Academia academia = academias.get(0);
        
        // Não permitir mudança de CNPJ
        if (!academia.getCnpj().equals(dto.getCnpj())) {
            throw new IllegalArgumentException("Não é permitido alterar o CNPJ da academia");
        }

        academiaMapper.updateEntity(dto, academia);
        Academia updated = academiaRepository.save(academia);
        return academiaMapper.toResponseDTO(updated);
    }

    /**
     * RF09: Dashboard com métricas de todas as academias (apenas ADMIN)
     */
    @Transactional(readOnly = true)
    public AcademiaDashboardDTO getDashboardMetrics() {
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

        return new AcademiaDashboardDTO(
                totalAcademies,
                totalStudents,
                totalInstructors,
                totalActive,
                totalInactive,
                averageStudents
        );
    }
}
