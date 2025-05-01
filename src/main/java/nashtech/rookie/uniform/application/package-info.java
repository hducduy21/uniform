/**
 * This module is responsible for managing user-related functionalities.
 * It includes user registration, authentication, and profile management.
 */
@ApplicationModule(
        displayName = "application",
        allowedDependencies = {"user::*", "shared::*"}
)
package nashtech.rookie.uniform.application;

import org.springframework.modulith.ApplicationModule;