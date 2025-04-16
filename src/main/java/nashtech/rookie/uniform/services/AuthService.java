package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;

public interface AuthService {
    TokenPair authenticate(AuthRequest authRequest);
}
