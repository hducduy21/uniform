package nashtech.rookie.uniform.user.internal.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.user.internal.entities.enums.EGender;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class UserGeneralResponse {
    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Enumerated(EnumType.STRING)
    private ERole role;
}
