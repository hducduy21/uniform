package nashtech.rookie.uniform.product.internal.services;

import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

import java.util.Collection;

public interface CategoryService {
    Collection<CategoryResponse> getAllCategories();
    Collection<CategoryDetailResponse> getAllDetailCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void updateCategoryStatus(Long id, ECategotyStatus status);
}

