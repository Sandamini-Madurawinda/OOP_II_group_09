package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Like;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.LikeRepository;
import g9.pulse.pulse.repository.PostRepository;
import g9.pulse.pulse.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class LikeController {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeController(LikeRepository likeRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {

        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/like")
    public Map<String, Object> toggleLike(@PathVariable Long id,
                                          Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Post post = postRepository.findById(id)
                .orElseThrow();

        Like existingLike = likeRepository
                .findByUserAndPost(user, post)
                .orElse(null);

        boolean liked;

        // UNLIKE
        if (existingLike != null) {

            likeRepository.delete(existingLike);

            liked = false;

        } else {

            // LIKE
            Like like = new Like();

            like.setUser(user);

            like.setPost(post);

            likeRepository.save(like);

            liked = true;
        }

        long likeCount = likeRepository.countByPost(post);

        Map<String, Object> response = new HashMap<>();

        response.put("liked", liked);

        response.put("likeCount", likeCount);

        return response;
    }
}


