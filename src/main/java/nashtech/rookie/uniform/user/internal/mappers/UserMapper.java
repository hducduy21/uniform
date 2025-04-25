package nashtech.rookie.uniform.user.internal.mappers;

import nashtech.rookie.uniform.user.dto.UserInfoDto;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserGeneralResponse;
import nashtech.rookie.uniform.user.internal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
    UserGeneralResponse userGeneralToUserGeneralResponse(User user);

    UserInfoDto userToUserInfoDto(User user);
}
