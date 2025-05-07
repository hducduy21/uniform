package nashtech.rookie.uniform.product.internal.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.product.internal.dtos.request.SizeRequest;
import nashtech.rookie.uniform.product.internal.dtos.response.SizeResponse;
import nashtech.rookie.uniform.product.internal.entities.SizeGroup;
import nashtech.rookie.uniform.product.internal.mappers.SizeMapper;
import nashtech.rookie.uniform.product.internal.repositories.SizeGroupRepository;
import nashtech.rookie.uniform.product.internal.services.SizeGroupService;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SizeGroupServiceImpl implements SizeGroupService {
    private final SizeGroupRepository sizeGroupRepository;
    private final SizeMapper sizeMapper;

    @Transactional(readOnly = true)
    @Override
    public List<SizeResponse> getAllSizes() {
        return sizeGroupRepository.findAll()
                .stream()
                .map(sizeMapper::sizeToSizeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public SizeResponse getSizeById(Integer id) {
        return sizeMapper.sizeToSizeResponse(findSizes(id));
    }

    @Transactional
    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        SizeGroup size = sizeMapper.sizeRequestToSize(sizeRequest);
        size = sizeGroupRepository.save(size);

        log.info("Created new size: {}", size);
        return sizeMapper.sizeToSizeResponse(size);
    }

    @Transactional
    @Override
    public SizeResponse updateSize(Integer id, SizeRequest sizeRequest) {
        SizeGroup size = findSizes(id);
        sizeMapper.updateSizeFromRequest(size, sizeRequest);
        sizeGroupRepository.save(size);

        log.info("Updated size: {}", size);
        return sizeMapper.sizeToSizeResponse(size);
    }

    private SizeGroup findSizes(Integer id) {
        return sizeGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found"));
    }

}
