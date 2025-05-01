package nashtech.rookie.uniform.product.internal.mappers;

import nashtech.rookie.uniform.product.internal.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.internal.entities.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "parent", ignore = true)
    Category categoryRequestToCategory(CategoryRequest request);
    CategoryResponse categoryToCategoryResponse(Category category);
    CategoryGeneralResponse categoryToCategoryGeneralResponse(Category category);
    CategoryDetailResponse categoryToCategoryDetailResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateCategoryFromRequest(@MappingTarget Category category, CategoryRequest categoryRequest);
}
