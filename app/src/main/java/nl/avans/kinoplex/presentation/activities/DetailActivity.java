package nl.avans.kinoplex.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("DetailActivity");

        // Check if any information was provided
        if(getIntent().getExtras() == null) {
            return;
        }

        // Get the provided movie data, logging the response
        String JSON = getIntent().getExtras().getString(Constants.INTENT_EXTRA_MOVIE_JSON);
        Log.d(Constants.DETAILACT_TAG, JSON);

        //Create a movie object  from JSON, logging the object information
        Movie movie = new JsonUtils<Movie>().parseToObject(JSON);

        Log.d(Constants.DETAILACT_TAG, movie.getId());


    }
}
