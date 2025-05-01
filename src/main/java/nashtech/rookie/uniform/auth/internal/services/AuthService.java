package nashtech.rookie.uniform.auth.internal.services;

import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
}
