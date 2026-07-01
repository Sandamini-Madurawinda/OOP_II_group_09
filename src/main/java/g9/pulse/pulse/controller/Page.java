package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Page {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/friends-page")
    public String friendsPage(Model model,
                              Authentication authentication) {

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow();

        model.addAttribute("currentUser", currentUser);

        return "friends";
    }

    @GetMapping("/requests-page")
    public String requestsPage(Model model,
                               Authentication authentication) {

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow();

        model.addAttribute("currentUser", currentUser);

        return "requests";
    }
}