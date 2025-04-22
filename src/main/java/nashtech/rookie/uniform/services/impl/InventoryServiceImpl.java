package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.InventoryRequest;
import nashtech.rookie.uniform.dtos.request.InventoryUpdationRequest;
import nashtech.rookie.uniform.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.entities.Inventory;
import nashtech.rookie.uniform.entities.ProductVariants;
import nashtech.rookie.uniform.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.mappers.InventoryMapper;
import nashtech.rookie.uniform.repositories.InventoryRepository;
import nashtech.rookie.uniform.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.services.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        ProductVariants productVariants = findProductVariantsById(inventoryRequest.getProductVariants());
        Inventory inventory = inventoryMapper.inventoryRequestToInventory(inventoryRequest);
        inventory.setProductVariants(productVariants);
        inventory = saveInventory(inventory);

        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse updateInventory(Long id, InventoryUpdationRequest inventoryUpdationRequest) {
        Inventory inventory = findInventoryById(id);
        inventory.setQuantityInStock(inventoryUpdationRequest.getQuantityInStock());
        inventory = saveInventory(inventory);

        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(inventoryMapper::inventoryToInventoryResponse)
                .toList();
    }

    @Override
    public InventoryResponse getInventoryById(Long id) {
        Inventory inventory = findInventoryById(id);
        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    private Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    private ProductVariants findProductVariantsById(Long id) {
        return productVariantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));
    }

    private Inventory findInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
    }
}
