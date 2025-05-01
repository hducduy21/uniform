package nashtech.rookie.uniform.product.api;

import nashtech.rookie.uniform.product.dto.ProductGeneralResponse;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.UUID;

public interface ProductServiceProvider {
    Page<ProductGeneralResponse> getProductsByIds(Collection<UUID> productIds, Pageable pageable);
    boolean productExists(UUID productId);

    Collection<ProductVariantsResponse> getProductVariantsByIds(Collection<Long> productVariantsIds);
    ProductVariantsResponse getProductVariantsById(Long productVariantsId);
    boolean productVariantsExists(Long productVariantsId);
}
