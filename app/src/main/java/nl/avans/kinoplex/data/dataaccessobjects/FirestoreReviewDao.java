package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import nl.avans.kinoplex.business.firestoreutils.FirestoreUtils;
import nl.avans.kinoplex.domain.Review;

public class FirestoreReviewDao implements DaoObject<Review> {

    private int movieId;

    public FirestoreReviewDao(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean create(Review review) {
        return false;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        // Create a document FirestoreUtils instance
        FirestoreUtils firestoreUtils = new FirestoreUtils(movieId);

        // Start the task to fill the given adapter
        //noinspection unchecked
        firestoreUtils.readIntoAdapter(new Pair<>("reviews", adapter));
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Review review) {
        return false;
    }

    @Override
    public boolean delete(Review review) {
        return false;
    }
}
