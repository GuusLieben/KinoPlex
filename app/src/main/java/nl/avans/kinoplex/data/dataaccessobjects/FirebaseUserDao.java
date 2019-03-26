package nl.avans.kinoplex.data.dataaccessobjects;

import android.util.Pair;

public class FirebaseUserDao implements DaoObject<Pair> {
    @Override
    public boolean create(Pair pair) {
        return false;
    }

    @Override
    public Pair read() {
        return null;
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
