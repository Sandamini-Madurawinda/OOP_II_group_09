package g9.pulse.pulse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // POST CONTENT
    @NotBlank(message = "Post content cannot be empty")
    @Size(max = 280, message = "Post cannot exceed 280 characters")
    @Column(nullable = false, length = 280)
    private String content;

    // CREATED TIME
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // AUTHOR DETAILS
    private String authorFirstName;
    private String authorLastName;

    // USER RELATIONSHIP
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // LIKE COUNT
    private int likeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostPrivacy privacy = PostPrivacy.PUBLIC;

    // UI ONLY FIELD
    @Transient
    private long dislikeCount = 0;

    // UI ONLY FIELD
    @Transient
    private boolean isLikedByCurrentUser;

    // UI ONLY FIELD
    @Transient
    private boolean isDislikedByCurrentUser;

    // UI ONLY COMMENTS
    @Transient
    private List<Comment> comments;

    // AUTO TIME
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean getIsLikedByCurrentUser() {
        return isLikedByCurrentUser;
    }

    public void setIsLikedByCurrentUser(boolean likedByCurrentUser) {
        this.isLikedByCurrentUser = likedByCurrentUser;
    }
    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public boolean getIsDislikedByCurrentUser() {
        return isDislikedByCurrentUser;
    }

    public void setIsDislikedByCurrentUser(boolean dislikedByCurrentUser) {
        this.isDislikedByCurrentUser = dislikedByCurrentUser;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public PostPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PostPrivacy privacy) {
        this.privacy = privacy;
    }

}