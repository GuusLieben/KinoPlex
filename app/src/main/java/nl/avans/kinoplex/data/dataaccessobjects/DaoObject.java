package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

public interface DaoObject<T> {

  boolean create(T t);

  void readIntoAdapter(RecyclerView.Adapter adapter);

  void readIntoIntent(Intent intent, Context context, Object id);

  void readAll(RecyclerView.Adapter adapter);

  boolean update(T t);

  boolean delete(T t);
}
