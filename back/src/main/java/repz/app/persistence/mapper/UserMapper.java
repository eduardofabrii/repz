package repz.app.persistence.mapper;

import org.springframework.stereotype.Component;

import repz.app.persistence.entity.User;
import repz.app.dto.response.UserGetResponse;

@Component
public class UserMapper {
    
    public UserGetResponse toResponse(User user) {
        return new UserGetResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getLastLogin(),
            user.getRole(),
            user.getActive()
        );
    }
}
