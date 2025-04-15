package nashtech.rookie.uniform.dtos.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UserGeneralResponse {
    private UUID id;

    private String email;

    private String phoneNumber;

    private String fullName;
}
