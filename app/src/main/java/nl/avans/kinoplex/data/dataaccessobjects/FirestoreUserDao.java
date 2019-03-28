package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

public class FirestoreUserDao implements DaoObject<Pair> {
  @Override
  public boolean create(Pair pair) {
    return false;
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {}

  @Override
  public void readAll(RecyclerView.Adapter adapter) {
    // TODO : Add this
  }

  @Override
  public boolean update(Pair pair) {
    return false;
  }

  @Override
  public boolean delete(Pair pair) {
    return false;
  }
}
