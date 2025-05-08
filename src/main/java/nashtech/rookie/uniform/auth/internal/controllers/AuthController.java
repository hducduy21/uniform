package nashtech.rookie.uniform.auth.internal.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.auth.internal.dtos.request.AuthRequest;
import nashtech.rookie.uniform.auth.internal.dtos.response.AuthResponse;
import nashtech.rookie.uniform.auth.internal.dtos.response.TokenPair;
import nashtech.rookie.uniform.auth.internal.dtos.response.UserResponse;
import nashtech.rookie.uniform.auth.internal.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication APIs")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenPair getAccessToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.getAccessToken(refreshToken);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserProfile() {
        return authService.getProfile();
    }

}
