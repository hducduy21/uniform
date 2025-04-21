package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;
import nashtech.rookie.uniform.entities.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productRequestToProduct(ProductRequest productRequest);
    ProductRequest productToProductRequest(Product product);
    ProductResponse productToProductResponse(Product product);
    ProductGeneralResponse productToProductGeneralResponse(Product product);
    ProductDetailsResponse productToProductDetailsResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateProductFromRequest(@MappingTarget Product product, ProductRequest productRequest);
}
