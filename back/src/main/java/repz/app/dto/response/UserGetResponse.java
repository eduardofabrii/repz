package repz.app.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import repz.app.domain.user.UserRole;

public record UserGetResponse(
    Integer id,
    String name,
    String email,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime lastLogin,
    
    UserRole role,
    Boolean active
) {}