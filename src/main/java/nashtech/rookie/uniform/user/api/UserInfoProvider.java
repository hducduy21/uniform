package nashtech.rookie.uniform.user.api;

import nashtech.rookie.uniform.user.dto.UserInfoDto;

import java.util.UUID;

public interface UserInfoProvider {
    UserInfoDto getUserInfo(String phoneNumber);

    String getEmail(String phoneNumber);
    String getPassword(String phoneNumber);
    String getRole(String phoneNumber);
    UUID getId(String phoneNumber);
    boolean isAccountNonLocked(String phoneNumber);
    boolean isAccountEnabled(String phoneNumber);
    boolean isPhoneNumberExists(String phoneNumber);
}
