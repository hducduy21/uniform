package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.response.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;

public interface AuthService {
    TokenPair authenticate(AuthRequest authRequest);

    void register(UserRegisterRequest userRegisterRequest);
}
