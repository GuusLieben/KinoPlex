package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.MovieList;

public class FirestoreListDao implements DaoObject<MovieList> {
  @Override
  public boolean create(MovieList movieList) {
    return false;
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {
    // Create a collection FirestoreUtils instance
    FirestoreUtils firestoreUtils = new FirestoreUtils(null);
    // Start the task to fill the given adapter
    //noinspection unchecked
    firestoreUtils.execute(new Pair<>(Constants.COL_LISTS, adapter));
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
