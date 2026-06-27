package g9.pulse.pulse.entity;
import g9.pulse.pulse.model.User;
import g9.pulse.pulse.model.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class FriendRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String status; // PENDING, ACCEPTED, REJECTED
}
