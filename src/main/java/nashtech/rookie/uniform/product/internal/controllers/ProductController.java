package nashtech.rookie.uniform.product.internal.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getAllProducts(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute ProductFilter productFilter
            ) {
        return productService.getProducts(pageable, productFilter);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @GetMapping(value = "/{productId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getProductImageById(@PathVariable UUID productId) {
        return productService.getProductImageById(productId);
    }

    @GetMapping("/{productId}/variants")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ProductVariantsResponse> getProductVariantsByProductId(@PathVariable UUID productId) {
        return productService.getProductVariantsByProductId(productId);
    }
}
