package nashtech.rookie.uniform.repositories;

import nashtech.rookie.uniform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
