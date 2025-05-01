package nashtech.rookie.uniform.auth.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.configuration.security.CustomUserDetails;
import nashtech.rookie.uniform.application.configuration.security.JwtUtil;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.mappers.AuthMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest authRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        TokenPair tokenPair = TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userDetails.getUser()))
                .refreshToken(jwtUtil.generateToken())
                .build();

        return authMapper.toAuthResponse(userDetails.getUser(), tokenPair);
    }

}
