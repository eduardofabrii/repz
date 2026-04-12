package repz.app.service.user;

import repz.app.dto.auth.RegistrationDTO;
import repz.app.dto.request.UserPutRequest;
import repz.app.dto.response.UserGetResponse;

import java.util.List;

public interface UserService {
    List<UserGetResponse> findAllUsers();

    UserGetResponse findUserById(Integer id);

    void updateLastLogin(String email);

    void registerUser(RegistrationDTO registrationDTO);

    void updateUser(Integer id, UserPutRequest userPutRequest);

    void deleteUser(Integer id);

    void restoreUser(Integer id);
}