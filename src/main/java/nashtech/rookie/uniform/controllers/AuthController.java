package nashtech.rookie.uniform.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.TokenPair;
import nashtech.rookie.uniform.dtos.request.AuthRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> login(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response) {
        TokenPair tokens = authService.authenticate(authRequest);

        setCookie(response, "accessToken", tokens.getAccessToken(), 7 * 24 * 60 * 60, "/");
        setCookie(response, "refreshToken", tokens.getRefreshToken(), 7 * 24 * 60 * 60, "/api/auth/refresh");

        return ApiResponse.<Void>builder()
                .message("Login successful")
                .success(true)
                .data(null)
                .build();
    }

    private static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

}
