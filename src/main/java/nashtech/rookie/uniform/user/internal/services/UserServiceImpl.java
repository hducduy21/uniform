package nashtech.rookie.uniform.user.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
}
