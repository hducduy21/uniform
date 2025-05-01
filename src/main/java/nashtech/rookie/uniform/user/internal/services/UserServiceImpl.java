package nashtech.rookie.uniform.user.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.mappers.UserMapper;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRegisterRequest userRegisterRequest) {
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
