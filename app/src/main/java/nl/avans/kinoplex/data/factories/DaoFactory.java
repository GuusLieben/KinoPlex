package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

public interface DaoFactory {
    DaoObject<Review> getReviewDao(int movieId);

    DaoObject<Movie> getMovieDao(int movieId);

    DaoObject<Movie> getMovieDao();

    DaoObject<MovieList> getListDao();

    DaoObject<Pair> getUserDao();
}
