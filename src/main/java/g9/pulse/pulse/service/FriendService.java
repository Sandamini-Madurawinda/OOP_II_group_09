package g9.pulse.pulse.service;
import g9.pulse.pulse.entity.FriendRequest;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.FriendRequestRepository;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (!fr.getStatus().equals("PENDING")) {
            return "Request already processed";
        }

        fr.setStatus("REJECTED");
        repo.save(fr);

        return "Friend request rejected";
    }

    // GET REQUESTS
    public List<FriendRequest> getRequests(Long userId) {
        return repo.findByReceiverIdAndStatus(userId, "PENDING");
    }

    public boolean areFriends(Long userId1, Long userId2) {
        boolean oneWay = repo.findBySenderIdAndReceiverIdAndStatus(userId1, userId2, "ACCEPTED").isPresent();
        boolean otherWay = repo.findBySenderIdAndReceiverIdAndStatus(userId2, userId1, "ACCEPTED").isPresent();
        return oneWay || otherWay;
    }
}