package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.User;
import g9.pulse.pulse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // VIEW PROFILE
    @GetMapping
    public String viewProfile(Principal principal, Model model) {

        String email = principal.getName();

        User user = userService.getByEmail(email);

        model.addAttribute("user", user);

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