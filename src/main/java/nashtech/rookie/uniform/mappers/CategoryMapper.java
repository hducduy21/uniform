package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.CategoryRequest;
import nashtech.rookie.uniform.dtos.response.CategoryDetailResponse;
import nashtech.rookie.uniform.dtos.response.CategoryGeneralResponse;
import nashtech.rookie.uniform.dtos.response.CategoryResponse;
import nashtech.rookie.uniform.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "parent", ignore = true)
    Category categoryRequestToCategory(CategoryRequest request);
    CategoryResponse categoryToCategoryResponse(Category category);
    CategoryGeneralResponse categoryToCategoryGeneralResponse(Category category);
    CategoryDetailResponse categoryToCategoryDetailResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateCategoryFromRequest(@MappingTarget Category category, CategoryRequest categoryRequest);
}
