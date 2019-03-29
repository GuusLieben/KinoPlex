package nl.avans.kinoplex.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MovieList extends DomainObject {
    private List<Movie> movieList;
    private static Set<MovieList> listSet = new LinkedHashSet<>();
    private String name;
    private String dbId;
    private int userId;

    public MovieList(String name, int userId) {
        this.name = name;
        this.userId = userId;
        movieList = new ArrayList<>();
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public void addMovie(Movie movie) {
        this.movieList.add(movie);
    }

    public void removeMovie(int movieId) {
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public static Set<MovieList> getListSet() {
        return listSet;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return dbId;
    }

    @Override
    public Map<String, Object> storeToMap() {
        return new HashMap<String, Object>() {
            {
                ArrayList<Object> movieIds = new ArrayList<>();
                for (Movie movie : movieList) movieIds.add(movie.getId());
                put("name", name);
                put("movies", movieIds);
                put("id", dbId);
                put("user_id", userId);
            }
        };
    }
}
