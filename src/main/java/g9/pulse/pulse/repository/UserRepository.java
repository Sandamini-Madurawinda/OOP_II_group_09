package g9.pulse.pulse.repository;

import g9.pulse.pulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

         Optional<User> findByEmail(String email);

         boolean existsByEmail(String email);


}
