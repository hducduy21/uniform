package nashtech.rookie.uniform.user.internal.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.user.internal.dtos.request.ChangePasswordRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserFilter;
import nashtech.rookie.uniform.user.internal.dtos.request.UserRegisterRequest;
import nashtech.rookie.uniform.user.internal.dtos.request.UserUpdateRequest;
import nashtech.rookie.uniform.user.internal.dtos.response.UserDetailResponse;
import nashtech.rookie.uniform.user.internal.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name="User", description = "User API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDetailResponse> getUser(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute UserFilter userFilter
    ) {
        return userService.getAllUser(pageable, userFilter);
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailResponse getUserProfile() {
        return userService.getUserProfile();
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.createUser(userRegisterRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/lock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserLockStatus(@PathVariable UUID id, @RequestParam boolean lock) {
        userService.updateLockedUser(id, lock);
    }

    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfile(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
    }
}
