package g9.pulse.pulse.repository;

import g9.pulse.pulse.model.Comment;
import g9.pulse.pulse.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}


