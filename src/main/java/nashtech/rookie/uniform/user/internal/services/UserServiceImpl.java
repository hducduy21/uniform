package nashtech.rookie.uniform.user.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.user.internal.dtos.request.ChangePasswordRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserFilter;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserUpdateRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;
import nashtech.rookie.uniform.user.internal.mappers.UserMapper;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import nashtech.rookie.uniform.user.internal.repositories.UserSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSpecificationBuilder userSpecificationBuilder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(readOnly = true)
    @Override
    public Page<UserDetailResponse> getAllUser(Pageable pageable, UserFilter userFilter) {
        // Just get customer
        userFilter.setRole(ERole.USER);

        Specification<User> spec = userSpecificationBuilder.build(userFilter);
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::userToUserDetailResponse);
    }

    @Transactional
    @Override
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

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public UserDetailResponse getUserProfile() {
        UUID userId = SecurityUtil.getCurrentUserId();
        User user = getUserById(userId);
        return userMapper.userToUserDetailResponse(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        UUID userId = SecurityUtil.getCurrentUserId();

        User user = getUserById(userId);
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        UUID userId = SecurityUtil.getCurrentUserId();
        User user = getUserById(userId);

        userMapper.userUpdateRequestToUser(user, userUpdateRequest);
        userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void updateLockedUser(UUID id, boolean locked) {
        User user = getUserById(id);
        user.setLocked(locked);
        userRepository.save(user);
    }


    private User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }
}
