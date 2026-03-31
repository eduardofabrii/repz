package repz.app.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import repz.app.persistence.entity.UserRole;

public record UserGetResponse(
    Long id,
    String name,
    String email,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime lastLogin,
    
    UserRole role,
    Boolean active
) {}