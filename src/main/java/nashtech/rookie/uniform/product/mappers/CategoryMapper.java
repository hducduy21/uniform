package nashtech.rookie.uniform.product.mappers;

import nashtech.rookie.uniform.product.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.product.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.product.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.product.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.product.entities.Category;
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
