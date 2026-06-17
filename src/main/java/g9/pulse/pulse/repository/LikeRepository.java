package g9.pulse.pulse.repository;

import g9.pulse.pulse.entity.Like;
import g9.pulse.pulse.entity.Post;
import g9.pulse.pulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    long countByPost(Post post);

    boolean existsByUserAndPost(User user, Post post);
}