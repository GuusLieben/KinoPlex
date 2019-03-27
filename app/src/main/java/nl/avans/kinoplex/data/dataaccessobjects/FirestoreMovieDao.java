package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Movie;

public class FirestoreMovieDao implements DaoObject<Movie> {

  @Override
  public boolean create(Movie movie) {
    return false;
  }

  public void read(RecyclerView.Adapter adapter, int movieId) {
    FirestoreUtils firestoreUtils = new FirestoreUtils(movieId);
    //noinspection unchecked
    firestoreUtils.execute(new Pair<>("movies", adapter));
  }

  @Override
  @Deprecated
  public void read(RecyclerView.Adapter adapter) {
    throw new UnsupportedOperationException("This method should not be used for movie collections");
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
