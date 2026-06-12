package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private final UserRepository userRepository;

    public SearchController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<User> users;

        if (keyword == null || keyword.isBlank()) {
            users = List.of();
        } else {
            users = userRepository.findByFirstNameContainingIgnoreCase(keyword);
        }

        model.addAttribute("users", users);

        return "search";
    }
}
