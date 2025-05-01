package nashtech.rookie.uniform.auth.internal.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {
    @Email
    @NotBlank(message = "Please provide a valid email.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
