package nashtech.rookie.uniform.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPair {
    private String accessToken;
    private String refreshToken;
}
