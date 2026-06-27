package g9.pulse.pulse.controller;

import g9.pulse.pulse.entity.FriendRequest;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import g9.pulse.pulse.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private FriendService service;

    @Autowired
    private UserRepository userRepo;

    // SEND REQUEST
    @PostMapping("/request/{id}")
    public String send(@PathVariable Long id,
                       @RequestParam Long senderId) {
        return service.sendRequest(senderId, id);
    }

    // ACCEPT REQUEST
    @PostMapping("/accept/{id}")
    public String accept(@PathVariable Long id) {
        return service.acceptRequest(id);
    }

    // REJECT REQUEST
    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        return service.rejectRequest(id);
    }

    // GET FRIEND REQUESTS (for logged user)
    @GetMapping("/requests")
    public List<FriendRequest> requests(@RequestParam Long userId) {
        return service.getRequests(userId);
    }

    // STEP 5 — GET ALL USERS (VERY SIMPLE)
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}