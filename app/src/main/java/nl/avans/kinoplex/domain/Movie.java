package nl.avans.kinoplex.domain;

import android.net.Uri;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie extends DomainObject {
  JsonObject jsonObject;
  List<Review> reviews;

  public Movie(JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public String getTitle() {
    return null;
  }

  public int getId() {
    return -1;
  }

  public int getRuntime() {
    return -1;
  }

  public Uri getPosterPath() {
    return null;
  }

  public String[] getGenres() {
    return null;
  }

  public String getTag() {
    return null;
  }

  public String getLanguage() {
    return null;
  }

  public String getOverview() {
    return null;
  }

  public Date getReleaseDate() {
    return null;
  }

  public void addReview(Review review) {
    this.reviews.add(review);
  }

  public List<Review> getReviews() {
    return reviews;
  }

  @Override
  public Map<String, Object> storeToMap() {
    return new HashMap<String, Object>() {
      {
        String[] keys = {
          "id",
          "title",
          "adult",
          "language",
          "release_date",
          "runtime",
          "overview",
          "poster",
          "trailer",
          "tagline",
          "genre"
        };
        for (String key : keys) {
            put(key, jsonObject.get(key));
        }
      }
    };
  }
}
