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

    public void handleLikeOrDislike(Long postId,
                                    User user,
                                    boolean actionIsDislike) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        Optional<Like> existingLike =
                likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {

            Like dbLike = existingLike.get();

            // remove existing reaction
            if (dbLike.isDislike() == actionIsDislike) {

                likeRepository.delete(dbLike);

            } else {

                // switch like/dislike
                dbLike.setDislike(actionIsDislike);
                likeRepository.save(dbLike);
            }

        } else {

            // create new reaction
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            newLike.setDislike(actionIsDislike);

            likeRepository.save(newLike);
        }
    }

    // total likes
    public long getLikeCount(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        return likeRepository.countByPostAndIsDislike(post, false);
    }

    // total dislikes
    public long getDislikeCount(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        return likeRepository.countByPostAndIsDislike(post, true);
    }

    // current user reaction
    public Like getUserAction(User user, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        return likeRepository
                .findByUserAndPost(user, post)
                .orElse(null);
    }
}

