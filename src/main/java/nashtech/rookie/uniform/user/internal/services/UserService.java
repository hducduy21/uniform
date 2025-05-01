package nashtech.rookie.uniform.user.internal.services;

import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;

public interface UserService {
    void createUser(UserRegisterRequest userRegisterRequest);
}
