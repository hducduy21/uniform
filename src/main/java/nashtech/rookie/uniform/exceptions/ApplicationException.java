package nashtech.rookie.uniform.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationException extends RuntimeException{
    private int code = 0;
    private HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, HttpStatus status, int code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}


