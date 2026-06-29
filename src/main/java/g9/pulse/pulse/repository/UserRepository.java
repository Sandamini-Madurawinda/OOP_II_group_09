package g9.pulse.pulse.repository;

import g9.pulse.pulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

         Optional<User> findByEmail(String email);

         boolean existsByEmail(String email);

    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    @Query("SELECT u FROM User u WHERE LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByFullNameContaining(@Param("keyword") String keyword);


}
