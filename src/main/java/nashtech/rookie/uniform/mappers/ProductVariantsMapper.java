package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.response.ProductVariantsResponse;
import nashtech.rookie.uniform.entities.ProductVariants;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductVariantsMapper {
    ProductVariantsResponse productVariantsToResponse(ProductVariants productVariants);
}
