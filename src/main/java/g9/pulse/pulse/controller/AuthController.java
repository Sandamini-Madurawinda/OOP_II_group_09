package g9.pulse.pulse.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import g9.pulse.pulse.service.UserService;
import g9.pulse.pulse.dto.RegistrationDto;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    UserService service;

    @GetMapping("/")
    public String landing(){
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("register", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("register") RegistrationDto dto,
            BindingResult result, // <-- Added the missing comma here
            Model model,
            RedirectAttributes redirectAttributes){

        if(result.hasErrors()) {
            return "register";
        }

        try {

            service.register(dto);


            redirectAttributes.addFlashAttribute("success", "Successfully registered! Please log in.");
            return "redirect:/login";

        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";

    }

    @GetMapping("/edit-profile")
    public String editProfilePage() {
        return "edit-profile";

    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";

    }
}