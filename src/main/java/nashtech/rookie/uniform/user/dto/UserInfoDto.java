package nashtech.rookie.uniform.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class UserInfoDto {
    private UUID id;
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String role;
    private boolean locked;
    private boolean enabled;
}
