/**
 * Manages user wishlists for saving favorite products.
 * Provides functionality to add, view, and remove wishlist items.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "Wishlish",
        allowedDependencies = {"product::*", "user::*", "shared::*"}
)
package nashtech.rookie.uniform.wishlist;

import org.springframework.modulith.ApplicationModule;