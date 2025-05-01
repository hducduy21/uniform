package nashtech.rookie.uniform.application.configuration;

import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.exceptions.ApplicationException;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    //Handle for custom exception
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Void>> handle(ApplicationException e) {
        return new ResponseEntity<>(ResponseUtil.errorResponse(e.getCode(), e.getMessage()), e.getStatus());
    }

    //Handle for validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseUtil.errorResponse("Validation failed", errorMessages);
    }
}
