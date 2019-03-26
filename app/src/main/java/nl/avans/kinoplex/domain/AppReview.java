package nl.avans.kinoplex.domain;

public class AppReview implements Review {

    String id;
    String userId;
    String content;
    int rating;

    public AppReview(String id, String userId, String content, int rating) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
