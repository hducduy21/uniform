package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.SizeRequest;
import nashtech.rookie.uniform.dtos.response.SizeResponse;
import nashtech.rookie.uniform.entities.SizeGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SizeMapper {
    SizeMapper INSTANCE = Mappers.getMapper(SizeMapper.class);

    @Mapping(target = "sizeTitle", source = "name")
    @Mapping(target = "elements", source = "elements")
    SizeGroup sizeRequestToSize(SizeRequest sizeRequest);

    @Mapping(target = "id", ignore = true)
    void updateSizeFromRequest(@MappingTarget SizeGroup size, SizeRequest sizeRequest);

    SizeResponse sizeToSizeResponse(SizeGroup size);

}
