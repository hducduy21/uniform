package nashtech.rookie.uniform.auth.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.configuration.security.CustomUserDetails;
import nashtech.rookie.uniform.application.configuration.security.JwtUtil;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.dtos.response.UserResponse;
import nashtech.rookie.uniform.auth.internal.mappers.AuthMapper;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.user.api.UserInfoProvider;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final UserInfoProvider userInfoProvider;

    public AuthResponse authenticate(AuthRequest authRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        TokenPair tokenPair = TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userDetails.getUser()))
                .refreshToken(jwtUtil.generateToken(userDetails.getEmail()))
                .build();

        return authMapper.toAuthResponse(userDetails.getUser(), tokenPair);
    }

    @Override
    public UserResponse getProfile() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authMapper.toUserResponse(userDetails.getUser());
    }

    @Override
    public TokenPair getAccessToken(String refreshToken) {
        if(!StringUtils.startsWith(refreshToken, "Bearer")) {
            throw new BadRequestException("Invalid refresh token format");
        }

        String email = jwtUtil.extractClaim(StringUtils.substring(refreshToken, 7), "email");

        if (email == null) {
            throw new BadRequestException("Invalid refresh token");
        }

        UserInfoDto userInfoDto = userInfoProvider.getUserInfo(email);
        return TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userInfoDto))
                .refreshToken(jwtUtil.generateToken(email))
                .build();
    }
}
