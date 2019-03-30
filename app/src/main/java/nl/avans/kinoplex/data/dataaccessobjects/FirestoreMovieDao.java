package nl.avans.kinoplex.data.dataaccessobjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Date;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

import static android.content.ContentValues.TAG;

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
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movie.getId()))
                .set(movie.storeToMap())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movieId))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> ((AbstractAdapter) adapter).addToDataSet(getMovieFromSnapshot(documentSnapshot))
                );
    }

    private Movie getMovieFromSnapshot(DocumentSnapshot documentSnapshot) {
        System.out.println(documentSnapshot);
        String title = documentSnapshot.getString("title");
        int id = Integer.parseInt(documentSnapshot.getId());
        int runtime = Integer.parseInt(String.valueOf(documentSnapshot.get("runtime")));
        String posterPath = documentSnapshot.getString("poster");

        String tag = documentSnapshot.getString("tagline");
        String language = documentSnapshot.getString("language");
        String overview = documentSnapshot.getString("overview");
        @SuppressLint("SimpleDateFormat")
        Date releaseDate = documentSnapshot.getDate("release_date");
        //noinspection ConstantConditions
        boolean adult = documentSnapshot.getBoolean("adult");

        return new Movie(
                title,
                id,
                runtime,
                posterPath,
                adult,
                new String[]{},
                tag,
                language,
                overview,
                releaseDate);
    }

    public void readIntoList(MovieList movieList) {
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(movieId))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            movieList.addMovie(getMovieFromSnapshot(documentSnapshot));
                            ((FirestoreListDao) DataMigration.getFactory().getListDao()).addMovieToList(movieList, movieId);
                        });
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        db.collection(Constants.COL_MOVIES)
                .document(String.valueOf(id))
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            Movie movie = getMovieFromSnapshot(documentSnapshot);
                            String movieJson = new Gson().toJson(movie);
                            intent.putExtra(Constants.INTENT_EXTRA_MOVIE_JSON, movieJson);
                            context.startActivity(intent);
                        });
    }

    public void readAll(RecyclerView.Adapter adapter) {
        readIntoAdapter(adapter);
    }

    @Override
    public boolean update(Movie movie) {
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
