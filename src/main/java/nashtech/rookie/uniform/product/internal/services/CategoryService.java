package nashtech.rookie.uniform.product.internal.services;

import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

import java.util.List;

public interface CategoryService {
    List<CategoryGeneralResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void updateCategoryStatus(Long id, ECategotyStatus status);
}
