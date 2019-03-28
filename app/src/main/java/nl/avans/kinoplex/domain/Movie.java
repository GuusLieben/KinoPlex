package nl.avans.kinoplex.domain;

import android.net.Uri;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie extends DomainObject {
  List<Review> reviews;
  String title;
  int id;
  int runtime;
  Uri posterPath;
  String[] genres;
  String tag;
  String language;
  String overview;
  Date releaseDate;
  boolean adult;

  public Movie(
      String title,
      int id,
      int runtime,
      Uri posterPath,
      boolean adult,
      String[] genres,
      String tag,
      String language,
      String overview,
      Date releaseDate) {
    this.title = title;
    this.id = id;
    this.runtime = runtime;
    this.posterPath = posterPath;
    this.adult = adult;
    this.genres = genres;
    this.tag = tag;
    this.language = language;
    this.overview = overview;
    this.releaseDate = releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }

  public int getRuntime() {
    return runtime;
  }

  public Uri getPosterPath() {
    return posterPath;
  }

  public String[] getGenres() {
    return genres;
  }

  public String getTag() {
    return tag;
  }

  public String getLanguage() {
    return language;
  }

  public String getOverview() {
    return overview;
  }

  public Date getReleaseDate() {
    return releaseDate;
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
        put("id", id);
        put("title", title);
        put("adult", adult);
        put("language", language);
        put("release_date", releaseDate);
        put("runtime", runtime);
        put("overview", overview);
        put("poster", posterPath.toString());
        put("tagline", tag);
        put("genre", genres);
      }
    };
  }
}
