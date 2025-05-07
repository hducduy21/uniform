package nashtech.rookie.uniform.user.internal.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.user.internal.validations.PasswordMatches;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatches
public class ChangePasswordRequest {
    private String currentPassword;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @NotBlank(message = "Confirm password is required.")
    private String confirmPassword;
}
