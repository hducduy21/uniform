package nashtech.rookie.uniform.user.internal.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.shared.enums.EGender;
import nashtech.rookie.uniform.shared.enums.ERole;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class UserGeneralResponse {
    private UUID id;

    private String email;

    private String phoneNumber;

    private String fullName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Enumerated(EnumType.STRING)
    private ERole role;
}
