package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.PostRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/feed")
    public String showHomeFeed(Model model, Authentication authentication) { // Added Authentication parameter
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("posts", posts);
        model.addAttribute("postForm", new Post());

        // Safely pass the active logged-in user to the home feed UI
        if (authentication != null && authentication.isAuthenticated()) {
            String loggedInUserEmail = authentication.getName();
            User currentUser = userRepository.findByEmail(loggedInUserEmail).orElse(null);
            model.addAttribute("currentUser", currentUser);
        }

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
}