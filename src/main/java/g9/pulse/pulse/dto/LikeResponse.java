package g9.pulse.pulse.dto;

public class LikeResponse {
    private boolean liked;
    private boolean disliked;
    private long likeCount;
    private long dislikeCount;

    public LikeResponse(boolean liked, boolean disliked, long likeCount, long dislikeCount) {
        this.liked = liked;
        this.disliked = disliked;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    // Getters සහ Setters
    public boolean isLiked() { return liked; }
    public boolean isDisliked() { return disliked; }
    public long getLikeCount() { return likeCount; }
    public long getDislikeCount() { return dislikeCount; }
}