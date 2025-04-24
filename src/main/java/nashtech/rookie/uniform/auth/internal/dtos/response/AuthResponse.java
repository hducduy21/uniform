package nashtech.rookie.uniform.auth.internal.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthResponse {
    private String fullName;
    private String gender;
    private LocalDateTime birthday;
    private TokenPair tokens;
}
