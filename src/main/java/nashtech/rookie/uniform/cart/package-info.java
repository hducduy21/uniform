/**
 * Application module for managing shopping cart functionality.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "cart",
        allowedDependencies = {"product::*", "user::*", "shared::*"}
)
package nashtech.rookie.uniform.cart;

import org.springframework.modulith.ApplicationModule;