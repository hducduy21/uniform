package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.ListVariantsImageUploadationRequest;
import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

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

    void uploadProductImage(UUID productId, MultipartFile file);
    void uploadProductVariantsImage(UUID productId, ListVariantsImageUploadationRequest files);

    byte[] getProductImageById(UUID productId);
}
