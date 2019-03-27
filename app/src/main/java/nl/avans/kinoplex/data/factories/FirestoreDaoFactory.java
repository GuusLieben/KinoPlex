package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreMovieDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreReviewDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

public class FirestoreDaoFactory implements DaoFactory {

    // It's a factory, what do you think it does?

    @Override
    public DaoObject<Review> getReviewDao(int movieId) {
        return new FirestoreReviewDao();
    }

    @Override
    public DaoObject<Movie> getMovieDao(int movieId) {
        return new FirestoreMovieDao(movieId);
    }

    @Override
    public DaoObject<MovieList> getListDao() {
        return new FirestoreListDao();
    }

    @Override
    public DaoObject<Pair> getUserDao() {
        return new FirestoreUserDao();
    }
}
