package g9.pulse.pulse.dto;

public class LikeResponse {

    private boolean liked;
    private long likeCount;

    public LikeResponse() {
    }

    public LikeResponse(boolean liked, long likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }
}
