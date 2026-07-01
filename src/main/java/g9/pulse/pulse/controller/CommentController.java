package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Comment;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.CommentRepository;
import g9.pulse.pulse.repository.PostRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add/{postId}")
    public String addComment(@PathVariable("postId") Long postId,
                             @RequestParam String content,
                             Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);

        return "redirect:/posts/feed";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity<?> editComment(@PathVariable Long id,
                                         @RequestBody Map<String, String> body,
                                         Authentication authentication) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (!comment.getUser().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String newContent = body.get("content");
        if (newContent == null || newContent.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        comment.setContent(newContent.trim());
        commentRepository.save(comment);

        return ResponseEntity.ok(Map.of("content", comment.getContent()));
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteComment(@PathVariable Long id,
                                           Authentication authentication) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (!comment.getUser().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        commentRepository.delete(comment);
        return ResponseEntity.ok().build();
    }
}