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

    public String getId() {
        return String.valueOf(id);
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

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setPosterPath(Uri posterPath) {
        this.posterPath = posterPath;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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
                put("genre", "1");
            }
        };
    }
}
