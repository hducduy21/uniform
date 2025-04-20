package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;
import nashtech.rookie.uniform.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productRequestToProduct(ProductRequest productRequest);
    ProductRequest productToProductRequest(Product product);
    ProductResponse productToProductResponse(Product product);
    ProductGeneralResponse productToProductGeneralResponse(Product product);
    ProductDetailsResponse productToProductDetailsResponse(Product product);

    void updateProductFromRequest(@MappingTarget Product product, ProductRequest productRequest);
}
