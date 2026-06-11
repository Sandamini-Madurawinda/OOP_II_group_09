package controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import service.UserService;
import dto.RegistrationDto;


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

            model.addAttribute(
                    "register",
                    new RegistrationDto()
            );

            return "register";

        }

        @PostMapping("/register")
        public String register(
                @Valid @ModelAttribute("register")
                RegistrationDto dto,
                BindingResult result){


            if(result.hasErrors())
            {
                return "register";
            }


            service.register(dto);


            return "redirect:/login";

        }



        @GetMapping("/home")
        public String home(){

            return "home";

        }


    }

