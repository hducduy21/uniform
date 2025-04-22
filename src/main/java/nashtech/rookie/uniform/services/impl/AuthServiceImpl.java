package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.configs.security.JwtUtil;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.TokenPair;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.exceptions.BadCredentialsException;
import nashtech.rookie.uniform.exceptions.BadRequestException;
import nashtech.rookie.uniform.mappers.UserMapper;
import nashtech.rookie.uniform.repositories.UserRepository;
import nashtech.rookie.uniform.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhoneNumber(), authRequest.getPassword()));
        User user = userRepository.findByPhoneNumber(authRequest.getPhoneNumber()).orElseThrow(
                () -> new BadCredentialsException("Invalid phone number or password")
        );

        return TokenPair.builder()
                .accessToken(jwtUtil.generateToken(user))
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
