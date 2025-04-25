package nashtech.rookie.uniform.product.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.SizeResponse;
import nashtech.rookie.uniform.product.internal.services.SizeGroupService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/size")
@RequiredArgsConstructor
public class SizeGroupController {
    private final SizeGroupService sizeGroupService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<SizeResponse>> getAllSizeGroups() {
        return ResponseUtil.successResponse(sizeGroupService.getAllSizes());
    }

    @GetMapping("/{sizeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SizeResponse> getSizeGroupById(@PathVariable Integer sizeId) {
        return ResponseUtil.successResponse(sizeGroupService.getSizeById(sizeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SizeResponse> createSizeGroup(@RequestBody @Valid SizeRequest sizeResponse) {
        return ResponseUtil.successResponse(sizeGroupService.createSize(sizeResponse));
    }

    @PutMapping("/{sizeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SizeResponse> updateSizeGroup(@PathVariable Integer sizeId, @RequestBody @Valid SizeRequest sizeResponse) {
        return ResponseUtil.successResponse(sizeGroupService.updateSize(sizeId, sizeResponse));
    }
}
