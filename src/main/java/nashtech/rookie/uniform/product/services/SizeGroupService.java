package nashtech.rookie.uniform.product.services;

import nashtech.rookie.uniform.product.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.dtos.response.SizeResponse;

import java.util.List;

public interface SizeGroupService {
    List<SizeResponse> getAllSizes();
    SizeResponse getSizeById(Integer id);
    SizeResponse createSize(SizeRequest sizeRequest);
    SizeResponse updateSize(Integer id, SizeRequest sizeRequest);
}
