package nl.avans.kinoplex.domain;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie extends DomainObject {
    List<TMDbReview> reviews;
    String title;
    int id;
    int runtime;
    String posterPath;
    List<String> genres;
    String tag;
    String language;
    String overview;
    Date releaseDate;
    boolean adult;
    Double rating;

    public Movie(
            String title,
            int id,
            int runtime,
            String posterPath,
            boolean adult,
            List<String> genres,
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

    public boolean isAdult() {
        return adult;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public String getFormattedRuntime() {
        int hours = runtime / 60;
        int minutes = runtime % 60;

        return hours + "h " + minutes + "m";
    }

    public Uri getPosterPath() {
        return Uri.parse(posterPath);
    }

    public List<String> getGenres() {
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

    public String getReleaseyear() {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        return formatYear.format(releaseDate);
    }

    public void addReview(TMDbReview review) {
        this.reviews.add(review);
    }

    public void setReviews(List<TMDbReview> reviews) {
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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setGenres(List<String> genres) {
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

    public List<TMDbReview> getReviews() {
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
                put("poster", posterPath);
                put("tagline", tag);
                put("genres", genres);
                put("rating_avg", rating);
            }
        };
    }
}
