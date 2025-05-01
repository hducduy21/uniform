package nashtech.rookie.uniform.auth.internal.services;

import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;

public interface AuthService {
    TokenPair authenticate(AuthRequest authRequest);
}
