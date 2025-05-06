package nashtech.rookie.uniform.user.internal.services;

import nashtech.rookie.uniform.user.internal.dtos.request.UserFilter;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserUpdateRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Page<UserDetailResponse> getAllUser(Pageable pageable, UserFilter userFilter);
    UserDetailResponse getUserProfile();

    void updateUser(UserUpdateRequest userUpdateRequest);

    void updateLockedUser(UUID id, boolean locked);
    void createUser(UserRegisterRequest userRegisterRequest);
}
