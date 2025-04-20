package nashtech.rookie.uniform.utils;

import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.configs.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
public class SecurityUtil {
    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
