package nashtech.rookie.uniform.product.services;

import nashtech.rookie.uniform.product.dtos.request.ListVariantsImageUploadationRequest;
import nashtech.rookie.uniform.product.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.dtos.request.ProductRequest;
import nashtech.rookie.uniform.product.dtos.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProductService {
    Page<ProductResponse> getProductsByAdmin(Pageable pageable, ProductFilter productFilter);

    Page<ProductResponse> getProducts(Pageable pageable, ProductFilter productFilter);

    ProductResponse getProductById(UUID productId);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(UUID productId, ProductRequest productRequest);

    void deleteProduct(UUID productId);

    void uploadProductImage(UUID productId, MultipartFile file);
    void uploadProductVariantsImage(UUID productId, ListVariantsImageUploadationRequest files);

    byte[] getProductImageById(UUID productId);
}
