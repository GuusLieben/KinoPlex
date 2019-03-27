package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.MovieList;

public class FirestoreListDao implements DaoObject<MovieList> {
  @Override
  public boolean create(MovieList movieList) {
    return false;
  }

  private volatile MovieList[] movieLists;

  @Override
  public void read(RecyclerView.Adapter adapter) {
    movieLists = null;
    FirestoreUtils firestoreUtils = new FirestoreUtils();
    //noinspection unchecked
    firestoreUtils.execute(new Pair<>("lists", adapter));
  }

  @Override
  public boolean update(MovieList movieList) {
    return false;
  }

  @Override
  public boolean delete(MovieList movieList) {
    return false;
  }
}
