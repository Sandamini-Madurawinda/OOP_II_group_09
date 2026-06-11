package controller;


import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostRepository postRepository;

    public PostRestController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Handles the async fetch link from toggleLike(postId)
    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> toggleLikeAction(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post entity row ID not found"));

        // Toggle like tracking calculation logic placeholder
        boolean updatedLikedState = !post.getIsLikedByCurrentUser();
        post.setIsLikedByCurrentUser(updatedLikedState);

        if (updatedLikedState) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
        }

        postRepository.save(post);

        // Build JSON response payload matching your JavaScript keys
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("liked", updatedLikedState);
        responseData.put("newLikeCount", post.getLikeCount());

        return ResponseEntity.ok(responseData);
    }
}