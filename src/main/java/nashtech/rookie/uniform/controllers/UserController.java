package nashtech.rookie.uniform.controllers;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
