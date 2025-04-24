/**
 * Application module for managing shopping cart functionality.
 *
 * @author Hoang Duc Duy
 */

@ApplicationModule(
        displayName = "Cart",
        allowedDependencies = {"product", "user"}
)
package nashtech.rookie.uniform.cart;

import org.springframework.modulith.ApplicationModule;