package nashtech.rookie.uniform.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerErrorException extends ApplicationException {

    private static final String DEFAULT_MESSAGE = "Something went wrong while setting up authentication. Please try again later!";

    public InternalServerErrorException() {
        super(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(int code) {
        super(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, code);
    }

    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
