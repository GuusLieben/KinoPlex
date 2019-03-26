package nl.avans.kinoplex.data.dataaccessobjects;

import nl.avans.kinoplex.domain.Review;

public class FirebaseReviewDao implements DaoObject<Review> {
    @Override
    public boolean create(Review review) {
        return false;
    }

    @Override
    public Review read() {
        return null;
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
