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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import nl.avans.kinoplex.business.firestoreutils.FirestoreUtils;
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
            documentSnapshot ->
                ((AbstractAdapter) adapter).addToDataSet(getMovieFromSnapshot(documentSnapshot)));
  }

  private Movie getMovieFromSnapshot(DocumentSnapshot documentSnapshot) {
    try {
      System.out.println(documentSnapshot.getData());
      String title = documentSnapshot.getString("title");
      int id = Integer.parseInt(documentSnapshot.getId());
      int runtime = ((Long) Objects.requireNonNull(documentSnapshot.get("runtime"))).intValue();
      String uriString = documentSnapshot.getString("poster_path");
      Uri posterPath = Uri.parse(uriString);
      String tag = documentSnapshot.getString("tagline");
      String language = documentSnapshot.getString("language");
      String overview = documentSnapshot.getString("overview");
      @SuppressLint("SimpleDateFormat")
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String value = documentSnapshot.getString("release_date");

      Date releaseDate = dateFormat.parse(value);
      //noinspection ConstantConditions
      boolean adult = documentSnapshot.getBoolean("adult");

      return new Movie(
          title,
          id,
          runtime,
          posterPath,
          adult,
          new String[] {},
          tag,
          language,
          overview,
          releaseDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void readIntoList(MovieList movieList) {
    db.collection(Constants.COL_MOVIES)
        .document(String.valueOf(movieId))
        .get()
        .addOnSuccessListener(
            documentSnapshot -> movieList.addMovie(getMovieFromSnapshot(documentSnapshot)));
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
              intent.putExtra("movieJson", movieJson);
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
