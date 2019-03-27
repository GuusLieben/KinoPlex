package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;

import nl.avans.kinoplex.domain.Review;

public class FirestoreReviewDao implements DaoObject<Review> {
    @Override
    public boolean create(Review review) {
        return false;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) { }

    @Override
    public boolean update(Review review) {
        return false;
    }

    @Override
    public boolean delete(Review review) {
        return false;
    }
}
