package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.response.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.services.AuthService;
import nashtech.rookie.uniform.utils.ResponseUtil;
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
        return ResponseUtil.successResponse("Login successful", authService.authenticate(authRequest));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        authService.register(userRegisterRequest);
        return ResponseUtil.successResponse("Register successfully", null);
    }
}
