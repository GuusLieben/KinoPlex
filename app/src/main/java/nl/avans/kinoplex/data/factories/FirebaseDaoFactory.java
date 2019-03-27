package nl.avans.kinoplex.data.factories;

import android.util.Pair;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.avans.kinoplex.data.dataaccessobjects.DaoObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.domain.Review;

public class FirebaseDaoFactory implements DaoFactory {

    private static final String DATABASE_URL = "https://kinoplex_ec6ec.firebaseio.com";

    public DatabaseReference setupConnection() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyCxXfgcwgAiRmD3cnqrvSIWTpeKDq74OTA")
                .setApplicationId("0:26032019:android:04042019")
                .setDatabaseUrl(DATABASE_URL)
                .setStorageBucket("kinoplex-ec6ec.appspot.com")
                .build();

//        FirebaseApp.initializeApp(MainActivity.singleton, options);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("movies");
        String key = databaseReference.child("550").getKey();
        System.out.println(key);
        return databaseReference;
    }

    @Override
    public DaoObject<Review> getReviewDao() {
        return null;
    }

    @Override
    public DaoObject<Movie> getMovieDao() {
        return null;
    }

    @Override
    public DaoObject<MovieList> getListDao() {
        return null;
    }

    @Override
    public DaoObject<Pair> getUserDao() {
        return null;
    }
}
