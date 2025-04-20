package nashtech.rookie.uniform.controllers;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.SizeRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.SizeResponse;
import nashtech.rookie.uniform.services.SizeGroupService;
import nashtech.rookie.uniform.utils.ResponseUtil;
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
    public ApiResponse<SizeResponse> createSizeGroup(@RequestBody SizeRequest sizeResponse) {
        return ResponseUtil.successResponse(sizeGroupService.createSize(sizeResponse));
    }

    @PutMapping("/{sizeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SizeResponse> updateSizeGroup(@PathVariable Integer sizeId, @RequestBody SizeRequest sizeResponse) {
        return ResponseUtil.successResponse(sizeGroupService.updateSize(sizeId, sizeResponse));
    }
}
