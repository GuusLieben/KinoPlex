package nl.avans.kinoplex.domain;

import java.util.HashMap;
import java.util.Map;

public class FireReview extends DomainObject implements Review {

    String id;
    String userId;
    String content;
    int rating;
    String movieId;

    public FireReview(String id, String userId, String content, int rating, String movieId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.movieId = movieId;
    }

    @Override
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

    @Override
    public Map<String, Object> storeToMap() {
        return new HashMap<String, Object>() {
            {
                put("id", id);
                put("user_id", userId);
                put("content", content);
                put("rating", rating);
                put("movie_id", movieId);
            }
        };
    }
}
