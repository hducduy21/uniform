package nashtech.rookie.uniform.product.internal.services;

import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.dtos.request.*;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.UUID;

public interface ProductService {
    Page<ProductResponse> getProductsByAdmin(Pageable pageable, ProductFilter productFilter);

    Page<ProductResponse> getProducts(Pageable pageable, ProductFilter productFilter);

    ProductResponse getProductById(UUID productId);
    ProductDetailsResponse getProductDetailById(UUID productId);

    UUID createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(UUID productId, ProductRequest productRequest);

    void deleteProduct(UUID productId);

    void uploadProductImage(UUID productId, MultipartFile file);
    void uploadProductVariantsImage(UUID productId, ListVariantsImageUploadationRequest files);

    byte[] getProductImageById(UUID productId);

    Collection<ProductVariantsResponse> getProductVariantsByProductId(UUID productId);

    void updateProductVariant(UUID productId, ProductVariantsRequest productVariantsRequest);

    void updateProductStatuses(BulkProductStatusUpdateRequest bulkProductStatusUpdateRequest);

}
