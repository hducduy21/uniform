package nashtech.rookie.uniform.product.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.Category;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;
import nashtech.rookie.uniform.product.internal.mappers.CategoryMapper;
import nashtech.rookie.uniform.product.internal.repositories.CategoryRepository;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryGeneralResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryGeneralResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.categoryToCategoryResponse(getCategory(id));
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category parent = null;
        if(categoryRequest.getParent() != null) {
            parent = getCategory(categoryRequest.getParent());
        }

        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        category.setParent(parent);
        category.setCreatedBy(SecurityUtil.getCurrentUserEmail());

        category = saveCategory(category);

        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategory(id);
        categoryMapper.updateCategoryFromRequest(category, categoryRequest);

        category.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        category.setUpdatedAt(LocalDateTime.now());

        saveCategory(category);
        return categoryMapper.categoryToCategoryDetailResponse(category);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
