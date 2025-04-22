package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.UserGeneralResponse;
import nashtech.rookie.uniform.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
    UserGeneralResponse userGeneralToUserGeneralResponse(User user);
}
