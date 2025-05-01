package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.product.dto.ProductGeneralResponse;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.mappers.ProductMapper;
import nashtech.rookie.uniform.product.internal.mappers.ProductVariantsMapper;
import nashtech.rookie.uniform.product.internal.repositories.ProductRepository;
import nashtech.rookie.uniform.product.internal.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSeviceProviderImpl implements ProductServiceProvider {
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductVariantsMapper productVariantsMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductGeneralResponse> getProductsByIds(Collection<UUID> productIds, Pageable pageable) {
        return productRepository.findAllByIdIn(productIds, pageable).map(productMapper::productToProductGeneralResponse);
    }

    @Override
    public boolean productExists(UUID productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public Collection<ProductVariantsResponse> getProductVariantsByIds(Collection<Long> productVariantsIds) {
        return productVariantsRepository.findAllByIdIn(productVariantsIds).stream().map(productVariantsMapper::productVariantsToResponse).toList();
    }

    @Override
    public ProductVariantsResponse getProductVariantsById(Long productVariantsId) {
        return productVariantsRepository.findById(productVariantsId)
                .map(productVariantsMapper::productVariantsToResponse)
                .orElseThrow(()-> new ResourceNotFoundException("Product variants not found"));
    }

    @Override
    public boolean productVariantsExists(Long productVariantsId) {
        return productVariantsRepository.existsById(productVariantsId);
    }
}
