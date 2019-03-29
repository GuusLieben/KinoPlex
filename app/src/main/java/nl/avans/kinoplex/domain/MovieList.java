package nl.avans.kinoplex.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MovieList extends DomainObject {
  private List<DomainObject> movieList;
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

  public void addMovie(DomainObject movie) {
    this.movieList.add((Movie) movie);
  }

  public void removeMovie(int movieId) {}

  public List<DomainObject> getDomainMovieList() {
    return movieList;
  }

  public List<Movie> getMovieList() {
    List<Movie> tempList = new ArrayList<>();
    for ( DomainObject obj : movieList ) {
      tempList.add((Movie) obj);
    }
    return tempList;
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
        Object[] movies = getMovieList().toArray(new Object[0]);
        put("name", name);
        put("movies", movies);
        put("id", dbId);
        put("user_id", userId);
      }
    };
  }
}
