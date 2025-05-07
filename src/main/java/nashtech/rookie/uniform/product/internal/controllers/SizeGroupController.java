package nashtech.rookie.uniform.product.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.SizeResponse;
import nashtech.rookie.uniform.product.internal.services.SizeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
public class SizeGroupController {
    private final SizeGroupService sizeGroupService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<SizeResponse> getAllSizeGroups() {
        return sizeGroupService.getAllSizes();
    }

    @GetMapping("/{sizeId}")
    @ResponseStatus(HttpStatus.OK)
    public SizeResponse getSizeGroupById(@PathVariable Integer sizeId) {
        return sizeGroupService.getSizeById(sizeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SizeResponse createSizeGroup(@RequestBody @Valid SizeRequest sizeRequest) {
        return sizeGroupService.createSize(sizeRequest);
    }

    @PutMapping("/{sizeId}")
    @ResponseStatus(HttpStatus.OK)
    public SizeResponse updateSizeGroup(@PathVariable Integer sizeId, @RequestBody @Valid SizeRequest sizeRequest) {
        return sizeGroupService.updateSize(sizeId, sizeRequest);
    }
}
