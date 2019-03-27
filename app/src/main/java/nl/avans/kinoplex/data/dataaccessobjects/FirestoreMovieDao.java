package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;

import nl.avans.kinoplex.domain.Movie;

public class FirestoreMovieDao implements DaoObject<Movie> {
  @Override
  public boolean create(Movie movie) {
    return false;
  }

  @Override
  public void read(RecyclerView.Adapter adapter) {}

  @Override
  public boolean update(Movie movie) {
    return false;
  }

  @Override
  public boolean delete(Movie movie) {
    return false;
  }
}
