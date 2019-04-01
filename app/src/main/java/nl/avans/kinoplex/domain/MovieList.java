package nl.avans.kinoplex.domain;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;

public class MovieList extends DomainObject {
    private List<Movie> movieList;
    private static Set<MovieList> listSet = new LinkedHashSet<>();
    private String name;
    private String dbId;
    private String userId;

    private boolean adapterIsSet;
    private boolean dataIsNew;

    //Adapter which uses this MovieList
    private MainMovieAdapter adapter;

    public MovieList(String name, String userId) {
        this.name = name;
        this.userId = userId;
        movieList = new ArrayList<>();
    }

    public void setAdapter(MainMovieAdapter adapter) {
        this.adapter = adapter;
        adapterIsSet = true;

        if(movieList.size() > 0 && dataIsNew) {
            for (Movie movie : movieList) {
                DataMigration.getFactory().getMovieDao(Integer.parseInt(movie.getId())).readIntoAdapter(adapter);
            }
        }
    }

    public void notifyAdapterOfNewData() {
        Log.d(Constants.MOVIELIST_TAG, "Size of movie list: " + movieList.size());
        dataIsNew = true;

        if(movieList.size() > 0 && adapterIsSet) {
            for (Movie movie : movieList) {
                DataMigration.getFactory().getMovieDao(Integer.parseInt(movie.getId())).readIntoAdapter(adapter);
            }
        }
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

    public List<DomainObject> getDomainMovieList() {
        System.out.println(movieList);
        List<DomainObject> domainList = new ArrayList<>(movieList);
        return domainList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public static Set<MovieList> getListSet() {
        return listSet;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static void setListSet(Set<MovieList> listSet) {
        MovieList.listSet = listSet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
