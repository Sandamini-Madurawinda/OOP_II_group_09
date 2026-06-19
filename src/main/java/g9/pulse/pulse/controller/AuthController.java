<<<<<<< HEAD:src/main/java/g9/pulse/pulse/controller/AuthController.java
package g9.pulse.pulse.controller;
=======
package controller;
>>>>>>> bbeb4b8 (Create authentication controller and registration DTO structure):src/main/java/controller/AuthController.java
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD:src/main/java/g9/pulse/pulse/controller/AuthController.java
import g9.pulse.pulse.service.UserService;
import g9.pulse.pulse.dto.RegistrationDto;
=======
import service.UserService;
import dto.RegistrationDto;
>>>>>>> bbeb4b8 (Create authentication controller and registration DTO structure):src/main/java/controller/AuthController.java


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

