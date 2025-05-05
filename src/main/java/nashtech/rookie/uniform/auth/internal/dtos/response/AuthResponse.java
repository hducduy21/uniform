package nashtech.rookie.uniform.auth.internal.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private TokenPair tokens;
    private UserResponse user;
}
