/**
 * Handles user reviews and ratings for products.
 * Supports creation, retrieval, and aggregation of review data.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "Review",
        allowedDependencies = {"product::*", "user::*", "shared::*"}
)
package nashtech.rookie.uniform.review;

import org.springframework.modulith.ApplicationModule;