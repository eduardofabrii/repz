package repz.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import repz.app.persistence.entity.UserRole;

import java.time.LocalDateTime;

public record UserGetResponse(
        Long id,
        String name,
        String email,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime lastLogin,

        UserRole role,
        Boolean active
) {}