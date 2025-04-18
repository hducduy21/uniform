package nashtech.rookie.uniform.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends ApplicationException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT); // 409 - Conflict
    }
}
