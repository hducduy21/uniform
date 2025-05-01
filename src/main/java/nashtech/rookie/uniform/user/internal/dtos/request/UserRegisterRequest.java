package nashtech.rookie.uniform.user.internal.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.user.internal.validations.Gender;
import nashtech.rookie.uniform.user.internal.validations.PasswordMatches;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PasswordMatches
public class UserRegisterRequest {
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @NotBlank(message = "Confirm password is required.")
    private String confirmPassword;

    @NotBlank(message = "Gender is required.")
    @Gender
    private String gender;

    private LocalDateTime birthday;
}
