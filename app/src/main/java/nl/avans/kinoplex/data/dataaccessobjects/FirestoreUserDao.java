package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

public class FirestoreUserDao implements DaoObject<Pair> {
    @Override
    public boolean create(Pair pair) {
        return false;
    }

    @Override
    public void read(RecyclerView.Adapter adapter) { }

    @Override
    public boolean update(Pair pair) {
        return false;
    }

    @Override
    public boolean delete(Pair pair) {
        return false;
    }
}
