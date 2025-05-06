package nashtech.rookie.uniform.user.internal.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFilter {
    private String email;
    private String phoneNumber;
    private Boolean locked;
    private Boolean enabled;
    private ERole role;
}
