package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.Category;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;
import nashtech.rookie.uniform.product.internal.mappers.CategoryMapper;
import nashtech.rookie.uniform.product.internal.repositories.CategoryRepository;
import nashtech.rookie.uniform.product.internal.services.CategoryService;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public Collection<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<CategoryResponse> getTreeCategories() {
        Collection<CategoryResponse> categories = categoryRepository
                .findAllByIsRoot(true).stream().map(categoryMapper::categoryToCategoryResponse)
                .toList();

        return categories.stream().map(res -> {
            Set<CategoryResponse> children = categoryRepository.findAllByParent_Id(res.getId()).stream()
                    .map(categoryMapper::categoryToCategoryResponse)
                    .collect(Collectors.toSet());
            res.setChildren(children);
            return res;
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<CategoryDetailResponse> getAllDetailCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDetailResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.categoryToCategoryResponse(getCategory(id));
    }

    @Transactional
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category parent = Optional.ofNullable(categoryRequest.getParent())
                .map(this::getCategory).orElse(null);

        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        category.setParent(parent);
        category.setCreatedBy(SecurityUtil.getCurrentUserEmail());

        categoryRepository.save(category);
        log.info("Create category with id: {}", category.getId());
        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Transactional
    @Override
    public CategoryDetailResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = getCategory(id);
        categoryMapper.updateCategoryFromRequest(category, categoryRequest);

        //Set parent category
        if (categoryRequest.getParent() != null) {
            validateParentCategory(categoryRequest.getParent(), id);
            Category parent = getCategory(categoryRequest.getParent());

            log.info("Update category with parent: {}", parent.getId());
            category.setParent(parent);
        }

        category.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        category.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(category);
        log.info("Update category with id: {}", id);
        return categoryMapper.categoryToCategoryDetailResponse(category);
    }

    @Transactional
    @Override
    public void updateCategoryStatus(Long id, ECategotyStatus status) {
        Category category = getCategory(id);
        category.setStatus(status);
        category.setUpdatedBy(SecurityUtil.getCurrentUserEmail());
        category.setUpdatedAt(LocalDateTime.now());

        log.info("Update category with id: {} to status: {}", id, status);
        categoryRepository.save(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private void validateParentCategory(Long parentId, Long categoryId) {
        if (parentId.equals(categoryId)) {
            throw new BadRequestException("Category cannot be its own parent");
        }
        Category parent = getCategory(parentId);
        if (!parent.isRoot()) {
            throw new BadRequestException("Parent category must be root category");
        }
    }
}
