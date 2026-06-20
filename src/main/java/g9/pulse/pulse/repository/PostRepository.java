package g9.pulse.pulse.repository;


import g9.pulse.pulse.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Custom query method to pull all posts ordered by timestamp (newest first)
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByContentContainingIgnoreCase(String content);

    long countByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);

}