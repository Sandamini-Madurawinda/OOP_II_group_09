package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.User;
import g9.pulse.pulse.service.PostService;
import g9.pulse.pulse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    public ProfileController(UserService userService,PostService postService) {
        this.userService = userService;
        this.postService = postService;

    }

    // VIEW PROFILE
    @GetMapping
    public String viewProfile(Principal principal, Model model) {

        String email = principal.getName();
        User user = userService.getByEmail(email);
        long postCount = postService.getPostCountByUser(user.getFirstName(), user.getLastName());

        long friendCount = 0;
        model.addAttribute("user", user);
        model.addAttribute("postCount", postCount);
        model.addAttribute("friendCount", friendCount);

        return "profile";
    }
    @GetMapping("/{id}")
    public String viewOtherProfile(@PathVariable("id") Long id, Model model) {
        User user = userService.getById(id);
        long postCount = postService.getPostCountByUser(user.getFirstName(), user.getLastName());
        long friendCount = 0;
        model.addAttribute("user", user);
        model.addAttribute("postCount", postCount);
        model.addAttribute("friendCount", friendCount);
        return "profile";
    }

    // EDIT PAGE
    @GetMapping("/edit")
    public String editPage(Principal principal, Model model) {

        String email = principal.getName();

        User user = userService.getByEmail(email);

        model.addAttribute("user", user);

        return "edit-profile";
    }

    // UPDATE PROFILE
    @PostMapping("/edit")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String bio,
                                Principal principal) {

        String email = principal.getName();

        userService.updateProfile(email, firstName, lastName, bio);

        return "redirect:/profile";
    }
}
