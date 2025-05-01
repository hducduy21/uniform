package nashtech.rookie.uniform.auth.internal.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private TokenPair tokens;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String gender;
}
