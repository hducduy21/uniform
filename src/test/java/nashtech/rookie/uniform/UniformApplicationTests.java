package nashtech.rookie.uniform;

import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.user.internal.entities.enums.ERole;
import nashtech.rookie.uniform.user.internal.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UniformApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		User user = User.builder()
				.email("user@gmail.com")
				.password(passwordEncoder.encode("123456789"))
				.phoneNumber("0123456789")
				.firstName("User")
				.lastName("User")
				.role(ERole.USER)
				.build();
		userRepository.save(user);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void verifyModuleStructure() {
		// Create ApplicationModules instance from the main application class
		ApplicationModules.of(UniformApplication.class).verify();

	}

}
