/**
 * Manages product, category, size information including creation and updates.
 * Exposes services for retrieving and maintaining product data.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "Product",
        allowedDependencies = {"user::*", "shared::*", "application::*"}
)
package nashtech.rookie.uniform.product;

import org.springframework.modulith.ApplicationModule;