package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.services.StorageService;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.dtos.request.ListVariantsImageUploadationRequest;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.entities.Category;
import nashtech.rookie.uniform.product.internal.entities.Product;
import nashtech.rookie.uniform.product.internal.entities.ProductVariants;
import nashtech.rookie.uniform.product.internal.entities.SizeGroup;
import nashtech.rookie.uniform.product.internal.entities.enums.EProductStatus;
import nashtech.rookie.uniform.product.internal.mappers.ProductMapper;
import nashtech.rookie.uniform.product.internal.mappers.ProductVariantsMapper;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SizeGroupRepository sizeGroupRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final StorageService awsS3Service;
    private final ProductVariantsMapper productVariantsMapper;
    private final AdminProductSpecificationBuilder adminProductSpecificationBuilder;
    private final UserProductSpecificationBuilder userProductSpecificationBuilder;

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional(readOnly = true)
    @Override
    public ProductDetailsResponse getProductDetailById(UUID productId) {
        return productMapper.productToProductDetailsResponse(getProduct(productId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @Override
    public UUID createProduct(ProductRequest productRequest) {
        SizeGroup sizeType = getSizes(productRequest.getSizeTypeId());
        Set<Category> categories = new HashSet<>(categoryRepository.findAllByIdIn(productRequest.getCategoryIds()));
        Product product = productMapper.productRequestToProduct(productRequest);

        product.setCategories(categories);
        product.setSizeType(sizeType);
        product.setCreatedBy(SecurityUtil.getCurrentUserEmail());
        product = saveProduct(product);

        createProductVariants(product, productRequest.getHexColors(), sizeType.getElements());

        return product.getId();
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
    public void uploadProductImage(UUID productId, MultipartFile file) {
        if(!FileUtil.isImage(file)){
            throw new BadRequestException("File is not an image");
        }

        Product product = getProduct(productId);
        String folder = productId.toString();
        String fileName = productId + FileUtil.getFileExtension(file.getOriginalFilename());
        try {
            awsS3Service.uploadFile(fileName, folder, file);
            saveProduct(product);
        }catch (Exception e){
            throw new InternalServerErrorException("Error uploading profile image! Please try later.");
        }
    }

    @Override
    public Collection<ProductVariantsResponse> getProductVariantsByProductId(UUID productId) {
        return productVariantsRepository
                .findAllByProduct_Id(productId).stream()
                .map(productVariantsMapper::productVariantsToResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @Override
    public void uploadProductVariantsImage(UUID productId, ListVariantsImageUploadationRequest listVariantsImageUploadationRequest) {
        String folder = productId.toString();
        Map<String, MultipartFile> files = productVariantsMapper.toImageMap(listVariantsImageUploadationRequest.getImages());
        awsS3Service.uploadFiles(files, folder);
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] getProductImageById(UUID productId) {
        if(!isProductExists(productId)) {
            throw new BadRequestException("Product not found");
        }
        try{
            return awsS3Service.getByte(productId.toString(), productId.toString());
        }catch (Exception e){
            throw new InternalServerErrorException("Error getting product image! Please try later.");
        }
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

    private boolean isProductExists(UUID productId) {
        return productRepository.existsById(productId);
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
