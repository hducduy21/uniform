package nashtech.rookie.uniform.inventory.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryUpdationRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import nashtech.rookie.uniform.inventory.internal.mappers.InventoryMapper;
import nashtech.rookie.uniform.inventory.internal.repositories.InventoryRepository;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    private final ProductServiceProvider productServiceProvider;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {

        if(!productServiceProvider.productVariantsExists(inventoryRequest.getProductVariantsId())) {
            throw new ResourceNotFoundException("Product not found");
        }

        Inventory inventory = inventoryMapper.inventoryRequestToInventory(inventoryRequest);
        inventory.setProductVariantsId(inventoryRequest.getProductVariantsId());
        inventory = saveInventory(inventory);

        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public InventoryResponse updateInventory(Long id, InventoryUpdationRequest inventoryUpdationRequest) {
        Inventory inventory = findInventoryById(id);
        inventory.setQuantityInStock(inventoryUpdationRequest.getQuantityInStock());
        inventory = saveInventory(inventory);

        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<InventoryResponse> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(inventoryMapper::inventoryToInventoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public InventoryResponse getInventoryById(Long id) {
        Inventory inventory = findInventoryById(id);
        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    private Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    private Inventory findInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
    }
}
