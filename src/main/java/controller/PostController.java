package controller;



import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.repository.PostRepository;
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

    // Constructor Injection
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 1. HTTP GET Mapping: Displays the home feed page populated with database rows
    @GetMapping("/home")
    public String showHomeFeed(Model model) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        // Inject variables that your Thymeleaf template layout expects to find
        model.addAttribute("posts", posts);
        model.addAttribute("postForm", new Post());

        return "home"; // Resolves strictly to home.html
    }

    // 2. HTTP POST Mapping: Handles form submission actions from post-form.html
    @PostMapping("/create")
    public String handleCreatePost(@Valid @ModelAttribute("postForm") Post post,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Re-fetch posts to display errors gracefully on the same screen
            model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
            return "home";
        }

        // Mocking user session data until Spring Security integration is configured
        post.setAuthorFirstName("Nadara");
        post.setAuthorLastName("Jayasinghe");

        postRepository.save(post);
        return "redirect:/posts/home"; // Refresh page to display the new post card
    }
}