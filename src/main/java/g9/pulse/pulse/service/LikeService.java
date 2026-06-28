package g9.pulse.pulse.service;

import g9.pulse.pulse.model.Like;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.LikeRepository;
import g9.pulse.pulse.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    public boolean toggleLike(Long postId, User user) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> existingLike =
                likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {

            likeRepository.delete(existingLike.get());

            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);

            return false;

        } else {

            Like like = new Like();
            like.setUser(user);
            like.setPost(post);

            likeRepository.save(like);

            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);

            return true;
        }
    }

    public long getLikeCount(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.countByPost(post);
    }
}
