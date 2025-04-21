package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.ProductRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.ProductResponse;
import nashtech.rookie.uniform.services.ProductService;
import nashtech.rookie.uniform.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<ProductGeneralResponse>> getAllProducts() {
        return ResponseUtil.successResponse(productService.getActiveProducts());
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ProductResponse> getProductById(@PathVariable UUID productId) {
        return ResponseUtil.successResponse(productService.getProductById(productId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseUtil.successResponse(productService.createProduct(productRequest));
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
