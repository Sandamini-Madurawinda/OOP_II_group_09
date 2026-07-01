package g9.pulse.pulse.controller;

import g9.pulse.pulse.dto.LikeResponse;
import g9.pulse.pulse.model.Like;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import g9.pulse.pulse.service.LikeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class LikeController {

    private final LikeService likeService;
    private final UserRepository userRepository;

    public LikeController(LikeService likeService, UserRepository userRepository) {
        this.likeService = likeService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/like")
    public LikeResponse toggleLike(@PathVariable Long id,
                                   @RequestParam boolean isDislike,
                                   Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();


        likeService.handleLikeOrDislike(id, user, isDislike);

        long likes = likeService.getLikeCount(id);
        long dislikes = likeService.getDislikeCount(id);

        Like current = likeService.getUserAction(user, id);
        boolean isLikedNow = (current != null && !current.isDislike());
        boolean isDislikedNow = (current != null && current.isDislike());

        return new LikeResponse(isLikedNow, isDislikedNow, likes, dislikes);
    }
}