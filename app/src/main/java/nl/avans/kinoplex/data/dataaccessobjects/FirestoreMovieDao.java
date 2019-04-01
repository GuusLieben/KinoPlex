package nl.avans.kinoplex.data.dataaccessobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

public class FirestoreMovieDao implements DaoObject<Movie> {

    private int movieId;
    private FirebaseFirestore db;

    public FirestoreMovieDao(int movieId) { // For specific movie
        this();
        this.movieId = movieId;
    }

    public FirestoreMovieDao() {
        db = FirestoreUtils.getInstance();
    } // For all movies

    @Override
    public boolean create(Movie movie) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to write to Firestore");
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movie.getId()))
                .set(movie.storeToMap())
                .addOnSuccessListener(aVoid -> Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(Constants.FIRESTOREMOVIEDAO_TAG, "Error writing document", e));
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to read movie into adapter");
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movieId))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> ((AbstractAdapter) adapter).addToDataSet(getMovieFromSnapshot(documentSnapshot))
                );
    }

    private Movie getMovieFromSnapshot(DocumentSnapshot documentSnapshot) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Collecting movie from snapshot : " + documentSnapshot.getData());
        String title = documentSnapshot.getString("title");
        int id = Integer.parseInt(documentSnapshot.getId());
        int runtime = Integer.parseInt(String.valueOf(documentSnapshot.get("runtime")));
        String uriString = documentSnapshot.getString("poster");
        String tag = documentSnapshot.getString("tagline");
        String language = documentSnapshot.getString("language");
        String overview = documentSnapshot.getString("overview");
        @SuppressLint("SimpleDateFormat")
        Date releaseDate = documentSnapshot.getDate("release_date");
        //noinspection ConstantConditions
        boolean adult = documentSnapshot.getBoolean("adult");
        Double rating = documentSnapshot.getDouble("rating_avg");
        List<String> genres = (List<String>) documentSnapshot.get("genres");
        if (genres == null) genres = new ArrayList<>();

        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Constructing movie");
        Movie movie = new Movie(
                title,
                id,
                runtime,
                uriString,
                adult,
                genres,
                tag,
                language,
                overview,
                releaseDate);
        movie.setRating(rating);

        return movie;
    }

    public void readIntoList(MovieList movieList) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to read movie into list");
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movieId))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot.getData() == null || documentSnapshot.getData().isEmpty()) {
                                ((TMDbMovieDao) DataMigration.getTMDbFactory().getMovieDao(movieId)).readIntoFirebase(movieId, movieList);
                            } else {
                                movieList.addMovie(getMovieFromSnapshot(documentSnapshot));
                                ((FirestoreListDao) DataMigration.getFactory().getListDao()).addMovieToList(movieList, movieId);
                            }
                        });
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to read movie into intent");
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(id))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            Movie movie = getMovieFromSnapshot(documentSnapshot);
                            String movieJson = new Gson().toJson(movie);
                            intent.putExtra("movieJson", movieJson);
                            Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Intent prepared");
                            if (movie.getTag().equals("")) {
                                Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Movie incomplete, loading from TMDb");
                                DataMigration.getTMDbFactory().getMovieDao().readIntoIntent(intent, context, movie);
                            } else context.startActivity(intent);
                        });
    }

    public void readAll(RecyclerView.Adapter adapter) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to read from Firestore");
        db.collection(Constants.COL_MOVIES).get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Reading document : " + documentSnapshot.getData());
                        if (documentSnapshot.getData() == null || documentSnapshot.getData().isEmpty() || documentSnapshot.get("runtime") == null) {
                            ((TMDbMovieDao) DataMigration.getTMDbFactory().getMovieDao(movieId)).readIntoFirebase(movieId, null);
                        } else {
                            Movie movie = getMovieFromSnapshot(documentSnapshot);
                            ((AbstractAdapter) adapter).addToDataSet(movie);
                        }
                    }
                }
        );
    }

    @Override
    public boolean update(Movie movie) {
        Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Attempting to update movie on Firestore");
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movie.getId()))
                .set(movie.storeToMap())
                .addOnSuccessListener(aVoid -> Log.d(Constants.FIRESTOREMOVIEDAO_TAG, "Updated movie"));
        return true;
    }

    @Override
    public boolean delete(Movie movie) {
        db.collection(Constants.COL_MOVIES).document(String.valueOf(movie.getId())).delete();
        return true;
    }
}
