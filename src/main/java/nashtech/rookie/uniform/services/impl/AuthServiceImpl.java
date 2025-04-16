package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.AuthResponse;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.repositories.UserRepository;
import nashtech.rookie.uniform.services.AuthService;
import nashtech.rookie.uniform.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TokenPair authenticate(AuthRequest authRequest) {
        Optional<User> userOptional = userRepository.findByEmailOrPhoneNumber(authRequest.getEmail(), authRequest.getPhoneNumber());

        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(authRequest.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return TokenPair.builder()
                .accessToken(jwtUtil.generateToken(userOptional.get()))
                .refreshToken(jwtUtil.generateToken())
                .build();

    }
}
