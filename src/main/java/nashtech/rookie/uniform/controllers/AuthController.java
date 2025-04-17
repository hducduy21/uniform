package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.response.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TokenPair> login(@RequestBody @Valid AuthRequest authRequest) {

        return ApiResponse.<TokenPair>builder()
                .message("Login successful")
                .success(true)
                .data(authService.authenticate(authRequest))
                .build();
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return authService.register(userRegisterRequest);
    }

}
