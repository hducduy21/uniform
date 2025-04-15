package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.UserGeneralResponse;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        return userService.createUser(userRegisterRequest);
    }

}
