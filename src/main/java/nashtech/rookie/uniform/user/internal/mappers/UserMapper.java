package nashtech.rookie.uniform.user.internal.mappers;

import nashtech.rookie.uniform.user.dto.UserInfoDto;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserUpdateRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import nashtech.rookie.uniform.user.internal.dtos.response.UserGeneralResponse;
import nashtech.rookie.uniform.user.internal.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
    UserGeneralResponse userToUserGeneralResponse(User user);
    UserDetailResponse userToUserDetailResponse(User user);
    UserInfoDto userToUserInfoDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void userUpdateRequestToUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
