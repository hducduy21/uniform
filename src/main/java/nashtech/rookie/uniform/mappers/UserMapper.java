package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.UserGeneralResponse;
import nashtech.rookie.uniform.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRegisterRequestToUser(UserRegisterRequest userRegisterRequest);
    UserGeneralResponse userGeneralToUserGeneralResponse(User user);
}
