package nashtech.rookie.uniform.configs;

import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.exceptions.ApplicationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ConfigExceptionHandler {

    //Handle for custom exception
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Void>> handle(ApplicationException e) {
        ApiResponse<Void> res = ApiResponse.<Void>builder()
                .message(e.getMessage())
                .errorCode(e.getCode())
                .success(false)
                .data(null)
                .build();
        return new ResponseEntity<>(res, e.getStatus());
    }


    //Handle for validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ApiResponse.<List<String>>builder()
                .message("Validation failed")
                .success(false)
                .data(errorMessages)
                .build();
    }
}
