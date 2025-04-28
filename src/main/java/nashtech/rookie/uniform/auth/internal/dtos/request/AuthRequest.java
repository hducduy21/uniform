package nashtech.rookie.uniform.auth.internal.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.shared.validations.RequirePhoneNumberOrEmail;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RequirePhoneNumberOrEmail
public class AuthRequest {
    private String phoneNumber;
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
