package nashtech.rookie.uniform.auth.internal.services;

import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.dtos.response.UserResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    UserResponse getProfile();
    TokenPair getAccessToken(String refreshToken);
}
