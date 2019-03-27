package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;

public interface DaoObject<T> {

    boolean create(T t);
    void read(RecyclerView.Adapter adapter);
    boolean update(T t);
    boolean delete(T t);
}
