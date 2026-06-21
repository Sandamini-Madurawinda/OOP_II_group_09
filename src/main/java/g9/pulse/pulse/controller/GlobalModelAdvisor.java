package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Post;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvisor {


    @ModelAttribute("postForm")
    public Post addPostFormToModel() {
        return new Post();
    }
}