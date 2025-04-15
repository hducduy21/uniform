package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.mappers.UserMapper;
import nashtech.rookie.uniform.repositories.UserRepository;
import nashtech.rookie.uniform.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<Void> createUser(UserRegisterRequest userRegisterRequest) {
        // Check if the email, phoneNumber already exists

        User user = UserMapper.INSTANCE.userRegisterRequestToUser(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ApiResponse.<Void>builder()
                .message("User created successfully")
                .success(true)
                .data(null)
                .build();
    }
}
