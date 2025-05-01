/**
 * This module is responsible for managing user-related functionalities.
 * It includes user registration, authentication.
 *
 * @author Hoang Duc Duy
 */
@ApplicationModule(
        displayName = "Authentication",
        allowedDependencies = {"shared::*", "application::security", "user::dto"}
)
package nashtech.rookie.uniform.auth;

import org.springframework.modulith.ApplicationModule;