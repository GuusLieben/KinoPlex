package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbGenreDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbListDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbMovieDao;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbReviewDao;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

public class TMDbDaoFactory implements DaoFactory {

    // It's a factory, what do you think it does?

    @Override
    public DaoObject<Review> getReviewDao(int movieId) {
        return new TMDbReviewDao(movieId);
    }

    @Override
    public DaoObject<Movie> getMovieDao(int movieId) {
        return new TMDbMovieDao(movieId);
    }

    @Override
    public DaoObject<Movie> getMovieDao() {
        return new TMDbMovieDao();
    }

    @Override
    public DaoObject<MovieList> getListDao() {
        return new TMDbListDao();
    }

    public DaoObject<Void> getGenreDao() {
        return new TMDbGenreDao();
    }

    @Override
    public DaoObject<Pair> getUserDao() {
        throw new UnsupportedOperationException();
    }
}
