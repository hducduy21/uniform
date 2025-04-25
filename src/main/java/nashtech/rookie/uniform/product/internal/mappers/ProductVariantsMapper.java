package nashtech.rookie.uniform.product.internal.mappers;

import nashtech.rookie.uniform.product.dto.response.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.dtos.request.ListVariantsImageUploadationRequest;
import nashtech.rookie.uniform.product.internal.entities.ProductVariants;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.utils.FileUtil;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductVariantsMapper {
    ProductVariantsResponse productVariantsToResponse(ProductVariants productVariants);

    default Map<String, MultipartFile> toImageMap(List<ListVariantsImageUploadationRequest.VariantsImageUploadationRequest> requests) {
        if (requests == null) {
            return null;
        }

        boolean isAllImage = requests.stream().allMatch(request-> FileUtil.isImage(request.getImage()));
        if(!isAllImage) {
            throw new BadRequestException("All files must be images");
        }

        return requests.stream()
                .filter(request -> request.getProductVariantsId() != null && request.getImage() != null)
                .collect(Collectors.toMap(
                        request -> String.valueOf(request.getProductVariantsId()),
                        ListVariantsImageUploadationRequest.VariantsImageUploadationRequest::getImage,
                        (existing, replacement) -> existing
                ));
    }
}
