package nashtech.rookie.uniform.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAcceptableException extends ApplicationException {
    public NotAcceptableException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE); // 406 - Not Acceptable
    }
}
