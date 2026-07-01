package g9.pulse.pulse.service;
import g9.pulse.pulse.entity.FriendRequest;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.FriendRequestRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private FriendRequestRepository repo;

    @Autowired
    private UserRepository userRepo;

    // SEND REQUEST
    public String sendRequest(Long senderId, Long receiverId) {

        if (senderId.equals(receiverId)) {
            return "You cannot send request to yourself";
        }

        Optional<FriendRequest> existing =
                repo.findBySenderIdAndReceiverId(senderId, receiverId);

        if (existing.isPresent()) {
            return "Request already exists";
        }

        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();

        FriendRequest fr = new FriendRequest();
        fr.setSender(sender);
        fr.setReceiver(receiver);
        fr.setStatus("PENDING");

        repo.save(fr);

        return "Request sent";
    }

    // ACCEPT REQUEST
    public String acceptRequest(Long requestId) {

        FriendRequest fr = repo.findById(requestId).orElseThrow();

        if (!fr.getStatus().equals("PENDING")) {
            return "Request already processed";
        }

        fr.setStatus("ACCEPTED");
        repo.save(fr);

        return "Friend request accepted";
    }

    // REJECT REQUEST
    public String rejectRequest(Long requestId) {

        FriendRequest fr = repo.findById(requestId).orElseThrow();

        repo.delete(fr);

        return "Friend request rejected";
    }

    public List<User> getSuggestedUsers(Long currentUserId) {

        List<User> users = userRepo.findAll();

        users.removeIf(user -> user.getId().equals(currentUserId));

        List<FriendRequest> allRequests = repo.findAll();

        users.removeIf(user ->

                allRequests.stream().anyMatch(fr ->

                        (
                                fr.getSender().getId().equals(currentUserId)
                                        && fr.getReceiver().getId().equals(user.getId())
                        )

                                ||

                                (
                                        fr.getReceiver().getId().equals(currentUserId)
                                                && fr.getSender().getId().equals(user.getId())
                                )
                )
        );

        return users;
    }

    public List<User> getFriends(Long userId) {

        List<FriendRequest> all = repo.findByStatus("ACCEPTED");

        List<User> friends = new ArrayList<>();

        for (FriendRequest fr : all) {

            if (fr.getSender().getId().equals(userId)) {
                friends.add(fr.getReceiver());
            }

            if (fr.getReceiver().getId().equals(userId)) {
                friends.add(fr.getSender());
            }
        }

        return friends;
    }

    public String unfriend(Long userId, Long friendId) {

        Optional<FriendRequest> fr = repo.findAll()
                .stream()
                .filter(r ->
                        r.getStatus().equals("ACCEPTED") &&
                                (
                                        (r.getSender().getId().equals(userId) && r.getReceiver().getId().equals(friendId))
                                                ||
                                                (r.getSender().getId().equals(friendId) && r.getReceiver().getId().equals(userId))
                                )
                )
                .findFirst();

        if (fr.isPresent()) {
            repo.delete(fr.get());
            return "Unfriended successfully";
        }

        return "Friend not found";
    }

    // GET REQUESTS
    public List<FriendRequest> getRequests(Long userId) {
        return repo.findByReceiverIdAndStatus(userId, "PENDING");
    }
}