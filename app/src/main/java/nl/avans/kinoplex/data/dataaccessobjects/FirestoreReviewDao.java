package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.FireReview;
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
        String id = "$NE";
        if (review instanceof FireReview) {
            if (((FireReview) review).getId() == null)
                id = db.collection(Constants.COL_REVIEWS).document().getId();
            else id = ((FireReview) review).getId();
        }
        ((FireReview) review).setId(id);
        if (!id.equals("$NE"))
            db.collection(Constants.COL_REVIEWS).document(id).set(((DomainObject) review).storeToMap());
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        db.collection(Constants.COL_REVIEWS)
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            String mId = String.valueOf(movieId);
                            List<Object> references = new ArrayList<>();
                            for (DocumentSnapshot snapshot : documentSnapshot.getDocuments()) {
                                if (Objects.requireNonNull(snapshot.getString("movie_id")).equals(mId))
                                    references.add(snapshot);
                            }
                            writeReferencesToAdapter(references, adapter);
                        });
    }

    private void writeReferencesToAdapter(List<Object> referenceList, RecyclerView.Adapter adapter) {
        for (Object reviewReference : referenceList) {
            db.collection(Constants.COL_REVIEWS)
                    .document(String.valueOf(((DocumentSnapshot) reviewReference).getId()))
                    .get()
                    .addOnSuccessListener(
                            documentSnapshot -> {
                                if (documentSnapshot.getString("user_id") != null) { // App review
                                    String id = documentSnapshot.getId();
                                    String author = documentSnapshot.getString("user_id");
                                    String content = documentSnapshot.getString("content");
                                    long rating = (long) documentSnapshot.get("rating");
                                    String reviewMovieID = documentSnapshot.getString("movie_id");
                                    int intRating = Math.round(rating);
                                    FireReview fireReview = new FireReview(id, author, content, intRating, reviewMovieID);
                                    ((AbstractAdapter) adapter).addToDataSet(fireReview);

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
        db.collection(Constants.COL_REVIEWS)
                .get()
                .addOnSuccessListener(
                        snapshots -> {
                            movie.setReviews(new ArrayList<>());
                            movie.setFireReviews(new ArrayList<>());

                            movie.setReviews(
                                    ((TMDbReviewDao)
                                            DataMigration.getTMDbFactory()
                                                    .getReviewDao(Integer.parseInt(movie.getId())))
                                            .getList());


                            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments())
                                if (Objects.requireNonNull(documentSnapshot.getString("movie_id"))
                                        .equalsIgnoreCase(String.valueOf(movieId))) {
                                    if (documentSnapshot.contains("user_id")) { // App review
                                        String id = documentSnapshot.getId();
                                        String author = documentSnapshot.getString("user_id");
                                        String content = documentSnapshot.getString("content");
                                        long rating = (long) documentSnapshot.get("rating");
                                        String reviewMovieID = documentSnapshot.getString("movie_id");
                                        int intRating = Math.round(rating);
                                        FireReview fireReview = new FireReview(id, author, content, intRating, reviewMovieID);
                                        movie.addAppReview(fireReview);
                                        System.out.println("Found a review");
                                    }
                                }

                            System.out.println("TMDb Reviews : " + movie.getReviews());
                            System.out.println("Fire Reviews : " + movie.getFireReviews());
                            int reviewAmount = movie.getReviews().size() + movie.getFireReviews().size();
                            detailActivity.setReviewText(String.valueOf(reviewAmount));
                        });
    }
}
