package repz.app.service.academia;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import repz.app.persistence.entity.User;
import repz.app.persistence.entity.UserRole;
import repz.app.persistence.repository.AcademiaRepository;
import repz.app.persistence.repository.PersonalRepository;
import repz.app.persistence.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AcademiaContextService {

    public static final String HEADER_NAME = "X-Academia-Id";

    private final UserRepository userRepository;
    private final AcademiaRepository academiaRepository;
    private final PersonalRepository personalRepository;

    public Long resolveOptional(Authentication auth, Long requestedAcademiaId) {
        User currentUser = getCurrentUser(auth);

        if (currentUser.getRole() == UserRole.ADMIN) {
            if (requestedAcademiaId != null) {
                assertAcademiaExists(requestedAcademiaId);
            }
            return requestedAcademiaId;
        }

        if (currentUser.getRole() == UserRole.USUARIO) {
            if (requestedAcademiaId != null) {
                assertAcademiaExists(requestedAcademiaId);
            }
            return requestedAcademiaId;
        }

        Long userAcademiaId = getUserAcademiaId(currentUser);
        validateRequestedAcademia(requestedAcademiaId, userAcademiaId);
        return userAcademiaId;
    }

    public Long resolveRequired(Authentication auth, Long requestedAcademiaId) {
        Long academiaId = resolveOptional(auth, requestedAcademiaId);
        if (academiaId == null) {
            throw new IllegalArgumentException("Header " + HEADER_NAME + " é obrigatório para selecionar a academia");
        }
        return academiaId;
    }

    private User getCurrentUser(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("Usuário não encontrado"));
    }

    private Long getUserAcademiaId(User user) {
        if (user.getRole() == UserRole.ACADEMIA) {
            return academiaRepository.findByResponsibleUserId(user.getId()).stream()
                    .findFirst()
                    .orElseThrow(() -> new AccessDeniedException("Usuário não possui academia vinculada"))
                    .getId();
        }

        if (user.getRole() == UserRole.PERSONAL) {
            return personalRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new AccessDeniedException("Personal não possui academia vinculada"))
                    .getAcademia()
                    .getId();
        }

        throw new AccessDeniedException("Perfil não possui contexto de academia vinculado");
    }

    private void validateRequestedAcademia(Long requestedAcademiaId, Long userAcademiaId) {
        if (requestedAcademiaId != null && !requestedAcademiaId.equals(userAcademiaId)) {
            throw new AccessDeniedException("Acesso negado para a academia informada");
        }
    }

    private void assertAcademiaExists(Long academiaId) {
        if (!academiaRepository.existsById(academiaId)) {
            throw new IllegalArgumentException("Academia não encontrada");
        }
    }
}
