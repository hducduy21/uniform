package nashtech.rookie.uniform.user.internal.controllers;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.user.internal.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
