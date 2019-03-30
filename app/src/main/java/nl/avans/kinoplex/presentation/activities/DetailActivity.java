package nl.avans.kinoplex.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;

public class DetailActivity extends AppCompatActivity {
    private ImageView movieBackdropImageView;

    private TextView movieTitleTextView;
    private TextView movieYearTextView;
    private TextView movieRuntimeTextView;
    private TextView movieGenreTextView;
    private TextView movieStatusTextView;
    private TextView movieDescriptionTextView;
    private TextView movieAvgRatingTextView;
    private TextView movieVoteNmbTextView;

    private RatingBar movieRatingBar;

    private Button movieShowReviews;
    private Button movieOptions;

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
        Movie movie = new Gson().fromJson(JSON, Movie.class);

        movieBackdropImageView = findViewById(R.id.iv_detail_movie_backdrop);

        movieTitleTextView = findViewById(R.id.tv_detail_movie_title);
        movieYearTextView = findViewById(R.id.tv_detail_movie_year);
        movieRuntimeTextView = findViewById(R.id.tv_detail_movie_runtime);
        movieGenreTextView = findViewById(R.id.tv_detail_movie_genre);
        movieStatusTextView = findViewById(R.id.tv_detail_movie_status);
        movieDescriptionTextView = findViewById(R.id.tv_detail_movie_description);
        movieAvgRatingTextView = findViewById(R.id.tv_detail_movie_avg_rating);
        movieVoteNmbTextView = findViewById(R.id.tv_detail_movie_vote_nmb);

        movieRatingBar = findViewById(R.id.rb_detail_movie_rating);

        movieShowReviews = findViewById(R.id.btn_detail_show_reviews);
        movieOptions = findViewById(R.id.btn_detail_options);

        Glide.with(this)
                .load(movie.getPosterPath())
                .into(movieBackdropImageView);

        movieTitleTextView.setText(movie.getTitle());
        movieYearTextView.setText(movie.getReleaseyear());
        movieRuntimeTextView.setText(movie.getFormattedRuntime());
        movieGenreTextView.setText("Action");
        movieStatusTextView.setText("Released");
        movieDescriptionTextView.setText(movie.getOverview());
        movieAvgRatingTextView.setText("8.2");
        movieVoteNmbTextView.setText("10594 ");

        movieRatingBar.setRating((float) 8.2 / 2);



    }
}
