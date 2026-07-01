package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Comment;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.CommentRepository;
import g9.pulse.pulse.repository.PostRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String addComment(@PathVariable("postId") Long postId, // <-- මෙතන "postId" ලෙස නිවැරදි කරා
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
}