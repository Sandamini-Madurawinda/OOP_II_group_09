package g9.pulse.pulse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Page {

    @GetMapping("/friends-page")
    public String friendsPage() {
        return "friends";
    }

    @GetMapping("/requests-page")
    public String requestsPage() {
        return "requests";
    }
}