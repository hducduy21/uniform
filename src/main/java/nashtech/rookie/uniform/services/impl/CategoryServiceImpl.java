package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.entities.Category;
import nashtech.rookie.uniform.entities.enums.ECategotyStatus;
import nashtech.rookie.uniform.exceptions.BadRequestException;
import nashtech.rookie.uniform.mappers.CategoryMapper;
import nashtech.rookie.uniform.repositories.CategoryRepository;
import nashtech.rookie.uniform.services.CategoryService;
import nashtech.rookie.uniform.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryGeneralResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper.INSTANCE::categoryToCategoryGeneralResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return CategoryMapper.INSTANCE.categoryToCategoryResponse(getCategory(id));
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category parent = null;
        if(categoryRequest.getParent() != null) {
            parent = getCategory(categoryRequest.getParent());
        }

        Category category = CategoryMapper.INSTANCE.categoryRequestToCategory(categoryRequest);
        category.setParent(parent);
        category.setCreatedBy(SecurityUtil.getCurrentUserEmail());

        category = saveCategory(category);

        return CategoryMapper.INSTANCE.categoryToCategoryResponse(category);
    }

    @Override
    public CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategory(id);
        CategoryMapper.INSTANCE.updateCategoryFromRequest(category, categoryRequest);

        category.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        category.setUpdatedAt(LocalDateTime.now());

        saveCategory(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDetailResponse(category);
    }

    @Override
    public void updateCategoryStatus(Long id, ECategotyStatus status) {
        Category category = getCategory(id);
        category.setStatus(status);
        category.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        category.setUpdatedAt(LocalDateTime.now());

        saveCategory(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Category not found"));
    }

    private Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
