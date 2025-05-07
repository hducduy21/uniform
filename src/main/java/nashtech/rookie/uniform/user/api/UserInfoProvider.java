package nashtech.rookie.uniform.user.api;

import nashtech.rookie.uniform.user.dto.UserInfoDto;

import java.util.UUID;

public interface UserInfoProvider {
    UserInfoDto getUserInfo(String email);
    UUID getId(String phoneNumber);
}
