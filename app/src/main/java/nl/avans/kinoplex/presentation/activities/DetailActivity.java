package nl.avans.kinoplex.presentation.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreReviewDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;

public class DetailActivity extends AppCompatActivity
        implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView movieBackdropImageView;

    private TextView movieTitleTextView;
    private TextView movieYearTextView;
    private TextView movieRuntimeTextView;
    private TextView movieGenreTextView;
    private TextView movieStatusTextView;
    private TextView movieDescriptionTextView;
    private TextView movieAvgRatingTextView;

    private RatingBar movieRatingBar;

    private Button movieShowReviews;
    private Button movieOptions;
    private Button backButton;
    private Movie movie;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("DetailActivity");

        // Check if any information was provided
        if (getIntent().getExtras() == null) {
            return;
        }

        // Get the provided movie data, logging the response
        String JSON = getIntent().getExtras().getString(Constants.INTENT_EXTRA_MOVIE_JSON);
        Log.d(Constants.DETAILACT_TAG, JSON);

        //Create a movie object  from JSON, logging the object information
        Movie movie = new Gson().fromJson(JSON, Movie.class);
        this.movie = movie;

        movieBackdropImageView = findViewById(R.id.iv_detail_movie_backdrop);

        movieTitleTextView = findViewById(R.id.tv_detail_movie_title);
        movieYearTextView = findViewById(R.id.tv_detail_movie_year);
        movieRuntimeTextView = findViewById(R.id.tv_detail_movie_runtime);
        movieGenreTextView = findViewById(R.id.tv_detail_movie_genre);
        movieStatusTextView = findViewById(R.id.tv_detail_movie_status);
        movieDescriptionTextView = findViewById(R.id.tv_detail_movie_description);
        movieAvgRatingTextView = findViewById(R.id.tv_detail_movie_avg_rating);

        movieRatingBar = findViewById(R.id.rb_detail_movie_rating);

        movieShowReviews = findViewById(R.id.btn_detail_show_reviews);
        movieOptions = findViewById(R.id.btn_detail_options);
        backButton = findViewById(R.id.view_detail_backbutton);

        movieShowReviews.setOnClickListener(this);
        movieOptions.setOnClickListener(this);
        backButton.setOnClickListener(this);

        Glide.with(this)
                .load(movie.getPosterPath())
                .into(movieBackdropImageView);

        String ratingString = String.valueOf(movie.getRating());
        float rating = Float.parseFloat(ratingString);

        movieTitleTextView.setText(movie.getTitle());
        movieYearTextView.setText(movie.getReleaseyear());
        movieRuntimeTextView.setText(movie.getFormattedRuntime());

        List<String> genreNames = new ArrayList<>();
        for (String g1 : movie.getGenres()) {
            System.out.println(g1);
            int id = 0;
            if (g1.contains("\"id\"")) {
                try {
                    JSONObject object = new JSONObject(g1);
                    id = object.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else id = Integer.parseInt(g1);
            String genreName = Constants.GENRES.get(id);
            genreNames.add(genreName);
        }

        String genres = String.join(", ", genreNames);

        movieGenreTextView.setText(genres);
        movieStatusTextView.setText("Released");
        movieDescriptionTextView.setText(movie.getOverview());
        movieAvgRatingTextView.setText(ratingString);
        movieRatingBar.setRating(rating / 2);

        setTitle(movie.getTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Asynchronously loads reviews into the movie
        ((FirestoreReviewDao) DataMigration.getFactory().getReviewDao(Integer.parseInt(movie.getId()))).getList(movie, this);
    }

    public void setReviewText(String text) {
        movieShowReviews.setText("Show reviews (" + text + ')');
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detail_options:
                Log.d(Constants.DETAILACT_TAG, "User clicked on the options button");

                PopupMenu pm = new PopupMenu(this, movieOptions);
                pm.getMenuInflater().inflate(R.menu.detail_options_popup, pm.getMenu());
                pm.setOnMenuItemClickListener(this);
                pm.show();
                break;

            case R.id.btn_detail_show_reviews:
                Log.d(Constants.DETAILACT_TAG, "User clicked on the 'Show Reviews' button");
                Intent reviews = new Intent(this, ReviewActivity.class);
                String movieJson = new Gson().toJson(movie);
                reviews.putExtra("movieJson", movieJson);
                startActivity(reviews);

                break;

            case R.id.view_detail_backbutton:
                finish();

                break;
        }
    }

    // Triggered when the user clicks on an item inside the PopupMenu
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_options_addtolist:
                Log.d(Constants.DETAILACT_TAG, "User wants to add the movie to a list...");
                Intent chooseListPopup = new Intent(getApplicationContext(), ChooseListPopUp.class);
                startActivity(chooseListPopup);
                break;

            case R.id.detail_options_addReview:
                Log.d(Constants.DETAILACT_TAG, "User wants to add a review to this movie...");
                Intent addReviewIntent = new Intent(this, AddReviewActivity.class);
                addReviewIntent.putExtra(Constants.MOVIE_ID, movie.getId());
                addReviewIntent.putExtra(Constants.MOVIE_TITLE, movie.getTitle());
                startActivity(addReviewIntent);
                break;

            case R.id.detail_options_share:
                Log.d(Constants.DETAILACT_TAG, "User wants to share this movie...");

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, movie.getPosterPath().toString());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                return super.onOptionsItemSelected(item);

        }


        return false;
    }

}
