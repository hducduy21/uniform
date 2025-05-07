package nashtech.rookie.uniform.product.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.dtos.request.*;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductDetailsResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductManagementController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getAllProductsByAdmin(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute ProductFilter productFilter
    ) {
        return productService.getProductsByAdmin(pageable, productFilter);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailsResponse get(@PathVariable UUID productId) {
        return productService.getProductDetailById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PatchMapping("/{productId}/image")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductImage(@PathVariable UUID productId, @RequestPart MultipartFile file) {
        productService.uploadProductImage(productId, file);
    }

    @PatchMapping("/{productId}/variants/image")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductVariantsImage(@PathVariable UUID productId, @ModelAttribute ListVariantsImageUploadationRequest files) {
        productService.uploadProductVariantsImage(productId, files);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable UUID productId, @RequestBody @Valid ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping("/{productId}/variants/price")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductVariant(@PathVariable UUID productId, @RequestBody @Valid ProductVariantsRequest productVariantsRequest) {
        productService.updateProductVariant(productId, productVariantsRequest);
    }

    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductStatuses(@RequestBody @Valid BulkProductStatusUpdateRequest bulkProductStatusUpdateRequest) {
        productService.updateProductStatuses(bulkProductStatusUpdateRequest);
    }
}
