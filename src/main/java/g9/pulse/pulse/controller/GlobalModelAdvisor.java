package g9.pulse.pulse.controller;

import g9.pulse.pulse.model.Post;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvisor {

    // This automatically injects a blank postForm object into EVERY template view,
    // fixing the 500 crash on /home without editing AuthController.
    @ModelAttribute("postForm")
    public Post addPostFormToModel() {
        return new Post();
    }
}