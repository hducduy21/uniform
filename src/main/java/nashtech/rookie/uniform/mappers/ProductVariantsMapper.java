package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.response.ProductVariantsResponse;
import nashtech.rookie.uniform.entities.ProductVariants;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductVariantsMapper {
    ProductVariantsMapper INSTANCE = Mappers.getMapper(ProductVariantsMapper.class);

    ProductVariantsResponse productVariantsToResponse(ProductVariants productVariants);
}
