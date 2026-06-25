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
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {

        this.postRepository = postRepository;
    }


    @GetMapping("/feed")
    public String showHomeFeed(Model model) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("posts", posts);
        model.addAttribute("postForm", new Post()); // Fixes the missing variable crash locally

        return "home";
    }


    @PostMapping("/create")
    public String handleCreatePost(@Valid @ModelAttribute("postForm") Post post,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("posts", postRepository.findAllByOrderByCreatedAtDesc());
            return "home";
        }


        post.setAuthorFirstName("Nadara");
        post.setAuthorLastName("Jayasinghe");

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