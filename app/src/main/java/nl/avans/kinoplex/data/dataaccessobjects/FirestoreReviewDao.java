package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.AppReview;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.Review;
import nl.avans.kinoplex.domain.TMDbReview;
import nl.avans.kinoplex.presentation.activities.DetailActivity;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

public class FirestoreReviewDao implements DaoObject<Review> {

    private int movieId;
    private FirebaseFirestore db;

    public FirestoreReviewDao(int movieId) {
        this.db = FirestoreUtils.getInstance();
        this.movieId = movieId;
    }

    @Override
    public boolean create(Review review) {
        String id = db.collection(Constants.COL_REVIEWS).document().getId();
        db.collection(Constants.COL_REVIEWS).document(id).set(((DomainObject) review).storeToMap());
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movieId))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            List<Object> reviewReferences = (List<Object>) documentSnapshot.get("reviews");
                            writeReferencesToAdapter(reviewReferences, adapter);
                        });
    }

    private void writeReferencesToAdapter(List<Object> referenceList, RecyclerView.Adapter adapter) {
        for (Object reviewReference : referenceList) {
            db.collection(Constants.COL_REVIEWS)
                    .document(String.valueOf(reviewReference))
                    .get()
                    .addOnSuccessListener(
                            documentSnapshot -> {
                                if (documentSnapshot.contains("user_id")) { // App review
                                    String id = documentSnapshot.getId();
                                    String author = documentSnapshot.getString("user_id");
                                    String content = documentSnapshot.getString("content");
                                    int rating = (int) documentSnapshot.get("rating");
                                    AppReview appReview = new AppReview(id, author, content, rating);
                                    ((AbstractAdapter) adapter).addToDataSet(appReview);

                                } else { // API Review
                                    String id = documentSnapshot.getId();
                                    String author = documentSnapshot.getString("user_id");
                                    String content = documentSnapshot.getString("content");
                                    TMDbReview tmDbReview = new TMDbReview(id, author, content);
                                    ((AbstractAdapter) adapter).addToDataSet(tmDbReview);
                                }
                            });
        }
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Review review) {
        db.collection(Constants.COL_REVIEWS)
                .document(((DomainObject) review).getId())
                .set(((DomainObject) review).storeToMap())
                .addOnSuccessListener(
                        aVoid -> Log.d(Constants.FIRESTOREREVIEWDAO_TAG, "Successfully updated review"));
        return true;
    }

    @Override
    public boolean delete(Review review) {
        db.collection(Constants.COL_REVIEWS).document(((DomainObject) review).getId()).delete();
        return true;
    }

    public void getList(Movie movie, DetailActivity detailActivity) {
        db.collection(Constants.COL_REVIEWS).get()
                .addOnSuccessListener(snapshots -> {
                    if (movie.getReviews() == null) movie.setReviews(new ArrayList<>());
                    for (DocumentSnapshot documentSnapshot : snapshots.getDocuments())
                        if (documentSnapshot.getString("movie_id").equalsIgnoreCase(String.valueOf(movieId))) {
                            String id = documentSnapshot.getString("id");
                            String author = documentSnapshot.getString("author");
                            String content = documentSnapshot.getString("content");
                            TMDbReview review = new TMDbReview(id, author, content);
                            movie.addReview(review);
                            System.out.println("Found a review");
                            detailActivity.setReviewText(String.valueOf(movie.getReviews().size()));
                        }
                    if (movie.getReviews().isEmpty())
                        movie.setReviews(((TMDbReviewDao) DataMigration.getTMDbFactory().getReviewDao(Integer.parseInt(movie.getId()))).getList());
                    detailActivity.setReviewText(String.valueOf(movie.getReviews().size()));
                });
    }
}
