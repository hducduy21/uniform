package nashtech.rookie.uniform.auth.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.configuration.security.CustomUserDetails;
import nashtech.rookie.uniform.application.configuration.security.JwtUtil;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public TokenPair authenticate(AuthRequest authRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhoneNumber(), authRequest.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userDetails.getUser()))
                .refreshToken(jwtUtil.generateToken())
                .build();
    }

}
