package nashtech.rookie.uniform.user.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.createUser(userRegisterRequest);
        return ResponseUtil.successResponse("Register successfully", null);
    }

}
