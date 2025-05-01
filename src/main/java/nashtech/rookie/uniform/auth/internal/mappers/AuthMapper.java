package nashtech.rookie.uniform.auth.internal.mappers;

import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(source = "userInfoDto.firstName", target = "firstName")
    @Mapping(source = "userInfoDto.lastName", target = "lastName")
    @Mapping(source = "userInfoDto.email", target = "email")
    @Mapping(source = "userInfoDto.phoneNumber", target = "phoneNumber")
    @Mapping(source = "userInfoDto.role", target = "role")
    @Mapping(source = "userInfoDto.gender", target = "gender")
    @Mapping(source = "tokenPair", target = "tokens")
    AuthResponse toAuthResponse(UserInfoDto userInfoDto, TokenPair tokenPair);
}
