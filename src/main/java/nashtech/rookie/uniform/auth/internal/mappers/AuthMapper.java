package nashtech.rookie.uniform.auth.internal.mappers;

import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.dtos.response.UserResponse;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(source = "userInfoDto", target = "user")
    AuthResponse toAuthResponse(UserInfoDto userInfoDto, TokenPair tokens);

    UserResponse toUserResponse(UserInfoDto userInfoDto);
}
