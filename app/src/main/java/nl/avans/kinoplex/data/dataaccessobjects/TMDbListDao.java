package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

public class TMDbListDao implements DaoObject {

    @Override
    public boolean create(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
//      Uri movieUri = Uri.parse("").buildUpon().appendQueryParameter();
    }

    @Override
    public boolean update(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Object o) {
        throw new UnsupportedOperationException();
    }
}
