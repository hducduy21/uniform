package nashtech.rookie.uniform.product.mappers;

import nashtech.rookie.uniform.product.dtos.request.ProductRequest;
import nashtech.rookie.uniform.product.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.product.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.product.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productRequestToProduct(ProductRequest productRequest);
    ProductRequest productToProductRequest(Product product);
    ProductResponse productToProductResponse(Product product);
    ProductGeneralResponse productToProductGeneralResponse(Product product);
    ProductDetailsResponse productToProductDetailsResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateProductFromRequest(@MappingTarget Product product, ProductRequest productRequest);
}
