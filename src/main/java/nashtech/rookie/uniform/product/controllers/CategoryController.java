package nashtech.rookie.uniform.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.product.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.services.CategoryService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.enums.ECategotyStatus;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<CategoryGeneralResponse>> getAllCategories() {
        return ResponseUtil.successResponse(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ResponseUtil.successResponse(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseUtil.successResponse(categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CategoryDetailResponse> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseUtil.successResponse(categoryService.updateCategory(categoryId, categoryRequest));
    }

    @PatchMapping("/{categoryId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateCategoryStatus(@PathVariable Long categoryId, @RequestParam ECategotyStatus status) {
        categoryService.updateCategoryStatus(categoryId, status);
        return ResponseUtil.successResponse("Category status updated successfully");
    }
}
