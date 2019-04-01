package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.FireReview;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Review;

public class AddReviewActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText contextView;
    private Button addReviewBtn;
    private TextView movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ratingBar = findViewById(R.id.rating_bar_add_review);
        contextView = findViewById(R.id.edit_text_add_review);
        addReviewBtn = findViewById(R.id.btn_add_review);
        movieTitle = findViewById(R.id.movie_title_add_review);

        if (getIntent().getExtras() == null) {
            return;
        }
        String movieId = getIntent().getStringExtra(Constants.MOVIE_ID);
        String movieTitleString = getIntent().getStringExtra(Constants.MOVIE_TITLE);

        movieTitle.setText(movieTitleString);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.ADDREVIEWACT_TAG, "User wants to add the Review for the movie " + movieId);
                String userId = Constants.pref.getString("userId", "-1");
                String content = contextView.getText().toString();
                float ratingValue = ratingBar.getRating() * 2;

                Review review = new FireReview(null, userId, content, Math.round(ratingValue), movieId);
                DataMigration.getFactory().getReviewDao(Integer.parseInt(movieId)).create(review);
                finish();
            }
        });
    }
}