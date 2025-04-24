package nashtech.rookie.uniform.inventory.internal.repositories;

import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
