package nashtech.rookie.uniform.shared.utils;

import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.shared.configurations.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@NoArgsConstructor
public class SecurityUtil {
        public static User getCurrentUser() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getUser();
            }
            throw new IllegalStateException("Principal is not instance of CustomUserDetails");
        }

        public static UUID getCurrentUserId() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getId();
            }
            throw new IllegalStateException("Principal is not instance of CustomUserDetails");
        }

        public static String getCurrentUserEmail() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getUser().getEmail();
            }
            throw new IllegalStateException("Principal is not instance of CustomUserDetails");
        }
}
