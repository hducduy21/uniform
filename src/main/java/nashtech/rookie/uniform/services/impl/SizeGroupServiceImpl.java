package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.SizeRequest;
import nashtech.rookie.uniform.dtos.response.SizeResponse;
import nashtech.rookie.uniform.entities.SizeGroup;
import nashtech.rookie.uniform.exceptions.BadRequestException;
import nashtech.rookie.uniform.mappers.SizeMapper;
import nashtech.rookie.uniform.repositories.SizeGroupRepository;
import nashtech.rookie.uniform.services.SizeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeGroupServiceImpl implements SizeGroupService {
    private final SizeGroupRepository sizeGroupRepository;
    private final SizeMapper sizeMapper;

    @Override
    public List<SizeResponse> getAllSizes() {
        return sizeGroupRepository.findAll()
                .stream()
                .map(sizeMapper::sizeToSizeResponse)
                .toList();
    }

    @Override
    public SizeResponse getSizeById(Integer id) {
        return sizeMapper.sizeToSizeResponse(findSizes(id));
    }

    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        SizeGroup size = sizeMapper.sizeRequestToSize(sizeRequest);
        size = saveSize(size);
        return sizeMapper.sizeToSizeResponse(size);
    }

    @Override
    public SizeResponse updateSize(Integer id, SizeRequest sizeRequest) {
        SizeGroup size = findSizes(id);
        sizeMapper.updateSizeFromRequest(size, sizeRequest);
        saveSize(size);
        return sizeMapper.sizeToSizeResponse(size);
    }

    private SizeGroup findSizes(Integer id) {
        return sizeGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Size not found"));
    }

    private SizeGroup saveSize(SizeGroup size) {
        return sizeGroupRepository.save(size);
    }
}
