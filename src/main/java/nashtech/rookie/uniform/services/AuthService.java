package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.response.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;

public interface AuthService {
    TokenPair authenticate(AuthRequest authRequest);

    ApiResponse<Void> register(UserRegisterRequest userRegisterRequest);
}
