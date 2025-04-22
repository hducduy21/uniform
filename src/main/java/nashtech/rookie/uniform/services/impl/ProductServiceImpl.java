package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;
import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.ProductVariants;
import nashtech.rookie.uniform.entities.SizeGroup;
import nashtech.rookie.uniform.entities.enums.EProductStatus;
import nashtech.rookie.uniform.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.mappers.ProductMapper;
import nashtech.rookie.uniform.repositories.ProductRepository;
import nashtech.rookie.uniform.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.repositories.SizeGroupRepository;
import nashtech.rookie.uniform.services.ProductService;
import nashtech.rookie.uniform.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SizeGroupRepository sizeGroupRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<ProductGeneralResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductGeneralResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductGeneralResponse> getActiveProducts() {
        return productRepository.findAllByStatus(EProductStatus.ACTIVE)
                .stream()
                .map(productMapper::productToProductGeneralResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductGeneralResponse> getActiveProductsByCategoryId(Long categoryId) {
        return productRepository.findAllByStatusAndCategory(EProductStatus.ACTIVE, categoryId)
                .stream()
                .map(productMapper::productToProductGeneralResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(UUID productId) {
        return productMapper.productToProductResponse(getProduct(productId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        SizeGroup sizeType = getSizes(productRequest.getSizeTypeId());

        Product product = productMapper.productRequestToProduct(productRequest);

        product.setCreatedBy(SecurityUtil.getCurrentUserEmail());

        product = saveProduct(product);

        createProductVariants(product, productRequest.getHexColors(), sizeType.getElements());

        return productMapper.productToProductResponse(product);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @Override
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        Product product = getProduct(productId);

        productMapper.updateProductFromRequest(product, productRequest);

        product.setUpdatedAt(LocalDateTime.now());
        product.setLastUpdatedBy(SecurityUtil.getCurrentUserEmail());

        saveProduct(product);

        return productMapper.productToProductResponse(product);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        Product product = getProduct(productId);
        product.setStatus(EProductStatus.DELETED);
        product.setUpdatedAt(LocalDateTime.now());
        product.setLastUpdatedBy(SecurityUtil.getCurrentUserEmail());

        saveProduct(product);
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    private SizeGroup getSizes(Integer sizeTypeId) {
        return sizeGroupRepository.findById(sizeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found"));
    }

    private void createProductVariants(Product product, Collection<String> hexCodes, Collection<String> sizes) {
        List<ProductVariants> productVariantsList = new ArrayList<>();
        for (String size : sizes) {
            for (String color : hexCodes) {
                ProductVariants productVariants = ProductVariants.builder()
                        .color(color)
                        .size(size)
                        .product(product)
                        .costPrice(product.getPrice())
                        .build();
                productVariantsList.add(productVariants);
            }
        }

        productVariantsRepository.saveAll(productVariantsList);
    }
}
