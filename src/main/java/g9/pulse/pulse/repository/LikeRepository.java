package g9.pulse.pulse.repository;

import g9.pulse.pulse.model.Like;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    long countByPost(Post post);

    long countByPostAndIsDislike(Post post, boolean isDislike);

    boolean existsByUserAndPost(User user, Post post);
}
