package nl.avans.kinoplex.data.dataaccessobjects;

import nl.avans.kinoplex.domain.Movie;

public class FirebaseMovieDao implements DaoObject<Movie> {
    @Override
    public boolean create(Movie movie) {
        return false;
    }

    @Override
    public Movie read() {
        return null;
    }

    @Override
    public boolean update(Movie movie) {
        return false;
    }

    @Override
    public boolean delete(Movie movie) {
        return false;
    }
}
