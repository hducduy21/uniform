package nashtech.rookie.uniform.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadCredentialsException extends ApplicationException {
    public BadCredentialsException(String message) {
        super(message, HttpStatus.BAD_REQUEST); // 400 - Bad Request
    }
}
