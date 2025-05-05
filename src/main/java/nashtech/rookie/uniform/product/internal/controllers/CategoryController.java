package nashtech.rookie.uniform.product.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;
import nashtech.rookie.uniform.product.internal.services.CategoryService;
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
    public Collection<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CategoryDetailResponse> getAllDetailCategories() {
        return categoryService.getAllDetailCategories();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDetailResponse updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @PatchMapping("/{categoryId}/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategoryStatus(@PathVariable Long categoryId, @RequestParam ECategotyStatus status) {
        categoryService.updateCategoryStatus(categoryId, status);
    }
}
