package nashtech.rookie.uniform.product.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.dtos.request.ListVariantsImageUploadationRequest;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.services.ProductService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<ProductResponse>> getAllProducts(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute ProductFilter productFilter
            ) {
        return ResponseUtil.successResponse(productService.getProducts(pageable, productFilter));
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ProductResponse> getProductById(@PathVariable UUID productId) {
        return ResponseUtil.successResponse(productService.getProductById(productId));
    }

    @GetMapping(value = "/{productId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getProductImageById(@PathVariable UUID productId) {
        return productService.getProductImageById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseUtil.successResponse(productService.createProduct(productRequest));
    }

    @PatchMapping("/{productId}/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> uploadProductImage(@PathVariable UUID productId, @RequestPart MultipartFile file) {
        productService.uploadProductImage(productId, file);
        return ResponseUtil.successResponse("Image uploaded successfully");
    }

    @PatchMapping("/{productId}/variants/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> uploadProductVariantsImage(@PathVariable UUID productId, @ModelAttribute ListVariantsImageUploadationRequest files) {
        productService.uploadProductVariantsImage(productId, files);
        return ResponseUtil.successResponse("Images uploaded successfully");
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ProductResponse> updateProduct(@PathVariable UUID productId, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseUtil.successResponse(productService.updateProduct(productId, productRequest));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseUtil.successResponse(null);
    }
}
