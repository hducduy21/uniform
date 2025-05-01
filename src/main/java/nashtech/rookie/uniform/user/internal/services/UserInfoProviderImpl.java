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
    public UserInfoDto getUserInfo(String phoneNumber) {
        return userMapper.userToUserInfoDto(getUser(phoneNumber));
    }

    @Override
    public String getEmail(String phoneNumber) {
        return getUser(phoneNumber).getEmail();
    }

    @Override
    public String getPassword(String phoneNumber) {
        return getUser(phoneNumber).getPassword();
    }

    @Override
    public String getRole(String phoneNumber) {
        return getUser(phoneNumber).getRole().toString();
    }

    @Override
    public UUID getId(String phoneNumber) {
        return getUser(phoneNumber).getId();
    }

    @Override
    public boolean isAccountNonLocked(String phoneNumber) {
        return !getUser(phoneNumber).getLocked();
    }

    @Override
    public boolean isAccountEnabled(String phoneNumber) {
        return getUser(phoneNumber).getEnabled();
    }

    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        try{
            getUser(phoneNumber);
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    private User getUser(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with phone: " + phoneNumber));
    }
}
