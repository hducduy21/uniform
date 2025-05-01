package nashtech.rookie.uniform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class UniformApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifyModuleStructure() {
		// Create ApplicationModules instance from the main application class
		ApplicationModules.of(UniformApplication.class).verify();

	}

}
