package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import g9.pulse.pulse.service.PostService;
import g9.pulse.pulse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private final UserService userService;
    private final PostService postService;


    public SearchController(UserService userService, PostService postService) {

        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<User> users =userService.searchUsersByName(keyword);
        List<Post> posts = postService.searchPostsByKeyword(keyword);


        model.addAttribute("users", users);
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);

        return "search";
    }
}
