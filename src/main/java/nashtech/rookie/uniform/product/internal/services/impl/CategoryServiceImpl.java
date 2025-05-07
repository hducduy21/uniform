package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    @Override
    public Collection<CategoryResponse> getTreeCategories() {
        Collection<CategoryResponse> categories = categoryRepository
                .findAllByIsRoot(true).stream().map(categoryMapper::categoryToCategoryResponse)
                .toList();

        return categories.stream().map(res->{
            Set<CategoryResponse> children = categoryRepository.findAllByParent_Id(res.getId()).stream()
                    .map(categoryMapper::categoryToCategoryResponse)
                    .collect(Collectors.toSet());
            res.setChildren(children);
            return res;
        }).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
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

        //Set parent category
        if(categoryRequest.getParent() != null){
            if(categoryRequest.getParent().equals(id)) {
                throw new BadRequestException("Category cannot be its own parent");
            }
            Category parent = getCategory(categoryRequest.getParent());
            category.setParent(parent);
        }

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
