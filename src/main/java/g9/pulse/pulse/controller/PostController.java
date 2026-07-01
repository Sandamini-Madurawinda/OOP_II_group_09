package g9.pulse.pulse.controller;
import g9.pulse.pulse.repository.LikeRepository;
import g9.pulse.pulse.repository.CommentRepository;
import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.model.Comment;
import g9.pulse.pulse.repository.PostRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import g9.pulse.pulse.model.Like;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public PostController(PostRepository postRepository,
                          UserRepository userRepository,
                          LikeRepository likeRepository,
                          CommentRepository commentRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

//    @GetMapping("/feed")
//    public String showHomeFeed(Model model, Authentication authentication) { // Added Authentication parameter
//        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
//
//        model.addAttribute("posts", posts);
//        model.addAttribute("postForm", new Post());
//
//        // Safely pass the active logged-in user to the home feed UI
//        if (authentication != null && authentication.isAuthenticated()) {
//            String loggedInUserEmail = authentication.getName();
//            User currentUser = userRepository.findByEmail(loggedInUserEmail).orElse(null);
//            model.addAttribute("currentUser", currentUser);
//        }
//
//        return "home";
//    }

    @GetMapping("/feed")
    public String showHomeFeed(Model model,
                               Authentication authentication) {

        List<Post> posts =
                postRepository.findAllByOrderByCreatedAtDesc();

        User currentUser = null;

        if (authentication != null
                && authentication.isAuthenticated()) {

            currentUser = userRepository
                    .findByEmail(authentication.getName())
                    .orElse(null);
        }

        // SET LIKE + COMMENTS
        for (Post post : posts) {

            // like count
            post.setLikeCount(
                    (int)likeRepository.countByPost(post)
            );

            // liked / disliked state
            if (currentUser != null) {

                likeRepository.findByUserAndPost(currentUser, post)
                        .ifPresentOrElse(like -> {
                            if (like.isDislike()) {
                                post.setIsDislikedByCurrentUser(true);
                            } else {
                                post.setIsLikedByCurrentUser(true);
                            }
                        }, () -> {});
            }

            // comments
            post.setComments(
                    commentRepository.findByPostOrderByCreatedAtAsc(post)
            );
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postForm", new Post());
        model.addAttribute("currentUser", currentUser);

        return "home";
    }

    @PostMapping("/create")
    public String handleCreatePost(@Valid @ModelAttribute("postForm") Post post,
                                   BindingResult result,
                                   Model model,
                                   Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
            return "home";
        }

        if (authentication != null && authentication.isAuthenticated()) {
            String loggedInUserEmail = authentication.getName();
            User currentUser = userRepository.findByEmail(loggedInUserEmail).orElse(null);

            if (currentUser != null) {
                post.setUser(currentUser);
                post.setAuthorFirstName(currentUser.getFirstName());
                post.setAuthorLastName(currentUser.getLastName());
            }
        } else {
            post.setAuthorFirstName("Anonymous");
            post.setAuthorLastName("User");
        }

        postRepository.save(post);
        return "redirect:/posts/feed";
    }
    @GetMapping("/{id}")
    public String viewSpecificPost(@PathVariable("id") Long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        model.addAttribute("post", post);
        return "home";



    }
}