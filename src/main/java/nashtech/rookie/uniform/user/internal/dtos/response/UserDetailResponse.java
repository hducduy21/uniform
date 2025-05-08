package nashtech.rookie.uniform.user.internal.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import nashtech.rookie.uniform.user.internal.entities.enums.EGender;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link nashtech.rookie.uniform.user.internal.entities.User}
 */
@Value
@Builder
@AllArgsConstructor
public class UserDetailResponse {
    UUID id;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    EGender gender;
    LocalDate birthday;
    ERole role;
    LocalDateTime createdAt;
    LocalDateTime lastLogin;
    Boolean locked;
    Boolean enabled;
}