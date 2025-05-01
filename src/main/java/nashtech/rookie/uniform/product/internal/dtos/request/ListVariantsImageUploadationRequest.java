package nashtech.rookie.uniform.product.internal.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListVariantsImageUploadationRequest {

    List<VariantsImageUploadationRequest> images;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class VariantsImageUploadationRequest {
        private Long productVariantsId;
        private MultipartFile image;
    }
}
