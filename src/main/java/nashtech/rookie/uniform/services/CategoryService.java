package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.entities.enums.ECategotyStatus;

import java.util.List;

public interface CategoryService {
    List<CategoryGeneralResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void updateCategoryStatus(Long id, ECategotyStatus status);
}
