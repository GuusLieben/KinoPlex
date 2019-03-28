package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.firestoreutils.FirestoreUtils;
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
    // Create a document FirestoreUtils instance
    FirestoreUtils firestoreUtils = new FirestoreUtils(movieId);
    // Start the task to fill the given adapter
    //noinspection unchecked
    firestoreUtils.readIntoAdapter(new Pair<>("movies", adapter));
  }

  public void readAll(RecyclerView.Adapter adapter) {
    FirestoreUtils firestoreUtils = new FirestoreUtils(null);
    firestoreUtils.readIntoAdapter(new Pair<>("movies", adapter));
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
