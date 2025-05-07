package nashtech.rookie.uniform.application.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.application.configuration.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@NoArgsConstructor
@Slf4j
public class SecurityUtil {

        public static UUID getCurrentUserId() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getId();
            }
            log.warn("Principal is not instance of CustomUserDetails");
            throw new IllegalStateException("Principal is not instance of CustomUserDetails");
        }

        public static String getCurrentUserEmail() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails customUserDetails) {
                return customUserDetails.getUser().getEmail();
            }
            log.warn("Principal is not instance of CustomUserDetails");
            throw new IllegalStateException("Principal is not instance of CustomUserDetails");
        }
}
