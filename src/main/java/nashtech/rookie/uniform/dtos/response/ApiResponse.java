package nashtech.rookie.uniform.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiResponse <T>{
    private String message;
    private T data;
    private boolean success;
}
