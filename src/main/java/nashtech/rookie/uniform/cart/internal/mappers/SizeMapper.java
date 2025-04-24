package nashtech.rookie.uniform.cart.internal.mappers;

import nashtech.rookie.uniform.product.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.dtos.response.SizeResponse;
import nashtech.rookie.uniform.product.entities.SizeGroup;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    @Mapping(target = "sizeTitle", source = "name")
    @Mapping(target = "elements", source = "elements")
    SizeGroup sizeRequestToSize(SizeRequest sizeRequest);
    SizeResponse sizeToSizeResponse(SizeGroup size);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateSizeFromRequest(@MappingTarget SizeGroup size, SizeRequest sizeRequest);

}
