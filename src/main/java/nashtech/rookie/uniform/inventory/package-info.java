/**
 * Handles inventory tracking and stock adjustments.
 * Provides services for updating and querying inventory data.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "Inventory",
        allowedDependencies = {"product::*", "user::*", "shared::*"}
)
package nashtech.rookie.uniform.inventory;

import org.springframework.modulith.ApplicationModule;