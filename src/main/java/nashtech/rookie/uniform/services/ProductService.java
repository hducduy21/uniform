package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductGeneralResponse> getProducts();
    List<ProductGeneralResponse> getActiveProducts();
    List<ProductGeneralResponse> getActiveProductsByCategoryId(Long categoryId);

    ProductResponse getProductById(UUID productId);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(UUID productId, ProductRequest productRequest);

    void deleteProduct(UUID productId);

}
