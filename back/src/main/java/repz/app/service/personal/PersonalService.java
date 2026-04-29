package repz.app.service.personal;

import lombok.AllArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import repz.app.dto.request.PersonalCreateRequest;
import repz.app.dto.request.PersonalUpdateRequest;
import repz.app.dto.response.AlunoResponse;
import repz.app.dto.response.PersonalAlunosResponse;
import repz.app.dto.response.PersonalResponse;
import repz.app.persistence.entity.Academia;
import repz.app.persistence.entity.Personal;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonalService {

    private final PersonalRepository personalRepository;
    private final UserRepository userRepository;
    private final AcademiaRepository academiaRepository;

    public PersonalResponse criarPersonal(PersonalCreateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário autenticado não encontrado"));

        if (currentUser.getRole() == UserRole.ACADEMIA) {
            Academia academia = academiaRepository.findById(request.getAcademiaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Academia não encontrada"));

            if (!academia.getResponsibleUser().getId().equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACADEMIA pode registrar personais apenas em sua própria unidade");
            }
        } else if (currentUser.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado para criar personal");
        }

        User user = userRepository.findById(Math.toIntExact(request.getUserId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário informado não encontrado"));

        Academia academia = academiaRepository.findById(request.getAcademiaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Academia informada não encontrada"));

        Personal personal = new Personal();
        personal.setUser(user);
        personal.setAcademia(academia);
        personal.setEspecialidade(request.getEspecialidade());
        personal.setAtivo(true);

        Personal saved = personalRepository.save(personal);
        return toDTO(saved);
    }

    public List<PersonalResponse> listarPersonais(Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (currentUser.getRole() == UserRole.ADMIN) {
            return personalRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else if (currentUser.getRole() == UserRole.ACADEMIA) {
            Academia academia = academiaRepository.findByResponsibleUserId(currentUser.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

            return personalRepository.findAll().stream()
                    .filter(p -> p.getAcademia().getId().equals(academia.getId()))
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("Acesso negado para listar personais");
    }

    public PersonalResponse obterPorId(Long id) {
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));
        return toDTO(personal);
    }

    public PersonalResponse atualizarPersonal(Long id, PersonalUpdateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        if (currentUser.getRole() == UserRole.ACADEMIA) {
            Academia academia = academiaRepository.findByResponsibleUserId(currentUser.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

            if (!personal.getAcademia().getId().equals(academia.getId())) {
                throw new RuntimeException("ACADEMIA pode editar apenas personais de sua unidade");
            }
        } else if (currentUser.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Acesso negado para editar personal");
        }

        personal.setEspecialidade(request.getEspecialidade());
        if (request.getAtivo() != null) {
            personal.setAtivo(request.getAtivo());
        }

        Personal updated = personalRepository.save(personal);
        return toDTO(updated);
    }

    public PersonalResponse inativarPersonal(Long id, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        if (currentUser.getRole() == UserRole.ACADEMIA) {
            Academia academia = academiaRepository.findByResponsibleUserId(currentUser.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

            if (!personal.getAcademia().getId().equals(academia.getId())) {
                throw new RuntimeException("ACADEMIA pode inativar apenas personais de sua unidade");
            }
        } else if (currentUser.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Acesso negado para inativar personal");
        }

        personal.setAtivo(false);
        Personal updated = personalRepository.save(personal);
        return toDTO(updated);
    }

    public PersonalResponse obterMeuPerfil(Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Personal personal = personalRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        return toDTO(personal);
    }

    public PersonalResponse atualizarMeuPerfil(PersonalUpdateRequest request, Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Personal personal = personalRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        personal.setEspecialidade(request.getEspecialidade());
        Personal updated = personalRepository.save(personal);
        return toDTO(updated);
    }

    public PersonalAlunosResponse obterMeusAlunos(Authentication auth) {
        User currentUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Personal personal = personalRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Personal não encontrado"));

        List<AlunoResponse> alunos = userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRole.USUARIO)
                .map(u -> new AlunoResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());

        return new PersonalAlunosResponse(
                personal.getId(),
                personal.getUser().getName(),
                personal.getEspecialidade(),
                personal.getAcademia().getId(),
                personal.getAcademia().getName(),
                alunos
        );
    }

    public void deletarPersonal(Long id) {
        personalRepository.deleteById(id);
    }

    private PersonalResponse toDTO(Personal personal) {
        return new PersonalResponse(
                personal.getId(),
                personal.getUser().getId(),
                personal.getUser().getName(),
                personal.getUser().getEmail(),
                personal.getAcademia().getId(),
                personal.getAcademia().getName(),
                personal.getEspecialidade(),
                personal.getAtivo()
        );
    }
}


