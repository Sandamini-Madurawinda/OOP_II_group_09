package g9.pulse.pulse.repository;

import g9.pulse.pulse.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status);
}

