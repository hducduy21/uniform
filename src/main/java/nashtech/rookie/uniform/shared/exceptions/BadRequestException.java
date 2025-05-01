package nashtech.rookie.uniform.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
