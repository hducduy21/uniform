package nashtech.rookie.uniform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic(sharedModules = {"shared"})
public class UniformApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniformApplication.class, args);
	}

}
