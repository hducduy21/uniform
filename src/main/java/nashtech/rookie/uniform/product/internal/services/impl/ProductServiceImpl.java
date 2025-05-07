package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.application.services.StorageService;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.inventory.api.InventoryProvider;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.dtos.request.*;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.entities.Category;
import nashtech.rookie.uniform.product.internal.entities.Product;
import nashtech.rookie.uniform.product.internal.entities.ProductVariants;
import nashtech.rookie.uniform.product.internal.entities.SizeGroup;
import nashtech.rookie.uniform.product.internal.entities.enums.EProductStatus;
import nashtech.rookie.uniform.product.internal.mappers.ProductMapper;
import nashtech.rookie.uniform.product.internal.mappers.ProductVariantsMapper;
import nashtech.rookie.uniform.product.internal.mappers.RatingCounterMapper;
import nashtech.rookie.uniform.product.internal.repositories.CategoryRepository;
import nashtech.rookie.uniform.product.internal.repositories.ProductRepository;
import nashtech.rookie.uniform.product.internal.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.product.internal.repositories.SizeGroupRepository;
import nashtech.rookie.uniform.product.internal.repositories.specs.AdminProductSpecificationBuilder;
import nashtech.rookie.uniform.product.internal.repositories.specs.UserProductSpecificationBuilder;
import nashtech.rookie.uniform.product.internal.services.ProductService;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.InternalServerErrorException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.shared.utils.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SizeGroupRepository sizeGroupRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final StorageService storageService;
    private final ProductVariantsMapper productVariantsMapper;
    private final AdminProductSpecificationBuilder adminProductSpecificationBuilder;
    private final UserProductSpecificationBuilder userProductSpecificationBuilder;
    private final InventoryProvider inventoryProvider;
    private final RatingCounterMapper ratingCounterMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getProductsByAdmin(Pageable pageable, ProductFilter productFilter) {
        Specification<Product> spec = adminProductSpecificationBuilder.build(productFilter);
        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::productToProductResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, ProductFilter productFilter) {
        Specification<Product> spec = userProductSpecificationBuilder.build(productFilter);
        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(productMapper::productToProductResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(UUID productId) {
        Product product = getProduct(productId);
        product.setViews(product.getViews() + 1);

        return productMapper.productToProductResponse(product);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDetailsResponse getProductDetailById(UUID productId) {
        return productMapper.productToProductDetailsResponse(getProduct(productId));
    }

    @Transactional
    @Override
    public UUID createProduct(ProductRequest productRequest) {
        SizeGroup sizeType = getSizes(productRequest.getSizeTypeId());
        Category category = getCategory(productRequest.getCategoryId());
        Product product = productMapper.productRequestToProduct(productRequest);

        product.setCategory(category);
        product.setSizeType(sizeType);
        product.setCreatedBy(SecurityUtil.getCurrentUserEmail());
        product = productRepository.save(product);

        createProductVariants(product, productRequest.getHexColors(), sizeType.getElements());
        log.info("Created product with id: {}", product.getId());
        return product.getId();
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        Product product = getProduct(productId);

        productMapper.updateProductFromRequest(product, productRequest);

        product.setUpdatedAt(LocalDateTime.now());
        product.setLastUpdatedBy(SecurityUtil.getCurrentUserEmail());

        productRepository.save(product);

        log.info("Updated product with id: {}", productId);
        return productMapper.productToProductResponse(product);
    }

    @Transactional
    @Override
    public void uploadProductImage(UUID productId, MultipartFile file) {
        if(!FileUtil.isImage(file)){
            throw new BadRequestException("File is not an image");
        }

        Product product = getProduct(productId);
        String folder = productId.toString();
        String fileName = productId.toString();
        try {
            storageService.uploadFile(fileName, folder, file);
            productRepository.save(product);
            log.info("Uploaded product image with id: {}", productId);
        }catch (Exception e){
            log.error("Error uploading product image: {}", e.getMessage());
            throw new InternalServerErrorException("Error uploading profile image! Please try later.");
        }
    }

    @Override
    public Collection<ProductVariantsResponse> getProductVariantsByProductId(UUID productId) {
        Collection<ProductVariants> productVariants = productVariantsRepository.findAllByProduct_Id(productId);

        Map<Long, Integer> quantityInStocks = inventoryProvider.getInventoryQuantityByProductVariantIds(
                productVariants.stream()
                        .map(ProductVariants::getId)
                        .toList()
        );
        return productVariantsMapper.productVariantsToResponses(productVariants, quantityInStocks);
    }

    @Transactional
    @Override
    public void uploadProductVariantsImage(UUID productId, ListVariantsImageUploadationRequest listVariantsImageUploadationRequest) {
        String folder = productId.toString();
        Map<String, MultipartFile> files = productVariantsMapper.toImageMap(listVariantsImageUploadationRequest.getImages());
        storageService.uploadFiles(files, folder);
        log.info("Uploaded product variants image with id: {}", productId);
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] getProductImageById(UUID productId) {
        if(!productRepository.existsById(productId)) {
            throw new BadRequestException("Product not found");
        }
        try{
            return storageService.getByte(productId.toString(), productId.toString());
        }catch (Exception e){
            log.error("Error getting product image: {}", e.getMessage());
            throw new InternalServerErrorException("Error getting product image! Please try later.");
        }
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        Product product = getProduct(productId);
        product.setStatus(EProductStatus.DELETED);
        product.setUpdatedAt(LocalDateTime.now());
        product.setLastUpdatedBy(SecurityUtil.getCurrentUserEmail());

        log.info("Updated status deleted product with id: {}", productId);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void updateProductVariant(UUID productId, ProductVariantsRequest productVariantsRequest) {
        Collection<ProductVariants> productVariants = productVariantsRepository.findAllByProduct_Id(productId);

        if (productVariants.isEmpty()) {
            log.warn("Product has no variants: " + productId);
            throw new ResourceNotFoundException("Product variants not found");
        }

        productVariants.stream()
                .filter(variant ->
                        productVariantsRequest.getProductVariantCostPriceMap()
                                .containsKey(variant.getId()))
                .forEach(variant -> variant.setCostPrice(
                        productVariantsRequest.getProductVariantCostPriceMap()
                                .get(variant.getId())));

        productVariantsRepository.saveAll(productVariants);
        log.info("Updated product variant with id: {}", productId);
    }

    @Transactional
    @Override
    public void updateProductStatuses(BulkProductStatusUpdateRequest bulkProductStatusUpdateRequest) {
        Collection<Product> products = productRepository.findAllByIdIn(bulkProductStatusUpdateRequest.getProductIds());
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found");
        }

        products.forEach(product ->
                product.setStatus(EProductStatus.valueOf(bulkProductStatusUpdateRequest.getStatus())));
        productRepository.saveAll(products);
        log.info("Updated product status with ids: {}", bulkProductStatusUpdateRequest.getProductIds());
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private SizeGroup getSizes(Integer sizeTypeId) {
        return sizeGroupRepository.findById(sizeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found"));
    }

    private void createProductVariants(Product product, Collection<String> hexCodes, Collection<String> sizes) {
        List<ProductVariants> productVariantsList = sizes.stream()
                .flatMap(size -> hexCodes.stream()
                        .map(color -> ProductVariants.builder()
                                .color(color)
                                .size(size)
                                .product(product)
                                .costPrice(product.getPrice())
                                .build()))
                .toList();

        productVariantsRepository.saveAll(productVariantsList);
    }
}