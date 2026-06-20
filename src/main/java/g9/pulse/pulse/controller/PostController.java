package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/posts") // Groups everything clearly under /posts
public class PostController {

    private final PostRepository postRepository;

    // Constructor Injection
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Displays the timeline home feed at http://localhost:8080/posts/feed
    @GetMapping("/feed")
    public String showHomeFeed(Model model) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("posts", posts);
        model.addAttribute("postForm", new Post()); // Fixes the missing variable crash locally

        return "home"; // Still points safely to your home.html template
    }

    // Handles form submission actions from your template card fragment
    @PostMapping("/create")
    public String handleCreatePost(@Valid @ModelAttribute("postForm") Post post,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
            return "home";
        }

        // Session identity mocks
        post.setAuthorFirstName("Nadara");
        post.setAuthorLastName("Jayasinghe");

        postRepository.save(post);
        return "redirect:/posts/feed"; // Redirects smoothly back to your active timeline view
    }
}