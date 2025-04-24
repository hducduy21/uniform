package nashtech.rookie.uniform.auth.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.user.internal.mappers.UserMapper;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import nashtech.rookie.uniform.shared.configurations.security.CustomUserDetails;
import nashtech.rookie.uniform.shared.configurations.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public TokenPair authenticate(AuthRequest authRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhoneNumber(), authRequest.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userDetails.getUser()))
                .refreshToken(jwtUtil.generateToken())
                .build();
    }

    public void register(UserRegisterRequest userRegisterRequest) {
        // Need optimize for check if the email, phoneNumber already exists
        if(userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        if(userRepository.existsByPhoneNumber(userRegisterRequest.getPhoneNumber())) {
            throw new BadRequestException("Phone number already in use");
        }

        User user = userMapper.userRegisterRequestToUser(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}
