package nashtech.rookie.uniform.user.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.user.api.UserInfoProvider;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.mappers.UserMapper;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoProviderImpl implements UserInfoProvider {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserInfoDto getUserInfo(String email) {
        return userMapper.userToUserInfoDto(getUser(email));
    }

    @Override
    public UUID getId(String phoneNumber) {
        return getUser(phoneNumber).getId();
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
