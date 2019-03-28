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
  private String userId;

  public MovieList(String name) {
    this.name = name;
    movieList = new ArrayList<>();
  }

  public void addMovie(Movie movie) {
    this.movieList.add(movie);
  }

  public void removeMovie(int movieId) {}

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
  public Map<String, Object> storeToMap() {
    return new HashMap<String, Object>() {
      {
        Object[] movies = movieList.toArray(new Object[0]);
        put("name", name);
        put("movies", movies);
      }
    };
  }
}
