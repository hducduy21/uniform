package nashtech.rookie.uniform.product.internal.services;

import nashtech.rookie.uniform.product.internal.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.SizeResponse;

import java.util.List;

public interface SizeGroupService {
    List<SizeResponse> getAllSizes();
    SizeResponse getSizeById(Integer id);
    SizeResponse createSize(SizeRequest sizeRequest);
    SizeResponse updateSize(Integer id, SizeRequest sizeRequest);
}
