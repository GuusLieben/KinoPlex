package nl.avans.kinoplex.data.dataaccessobjects;

import nl.avans.kinoplex.domain.MovieList;

public class FirebaseListDao implements DaoObject<MovieList> {
    @Override
    public boolean create(MovieList movieList) {
        return false;
    }

    @Override
    public MovieList read() {
        return null;
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
