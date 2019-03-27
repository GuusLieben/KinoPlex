package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Movie;

public class FirestoreMovieDao implements DaoObject<Movie> {

  private int movieId;

  public FirestoreMovieDao(int movieId) {
    this.movieId = movieId;
  }

  @Override
  public boolean create(Movie movie) {
    return false;
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {
    FirestoreUtils firestoreUtils = new FirestoreUtils(movieId);
    //noinspection unchecked
    firestoreUtils.execute(new Pair<>("movies", adapter));
  }

  @Override
  public boolean update(Movie movie) {
    return false;
  }

  @Override
  public boolean delete(Movie movie) {
    return false;
  }
}
