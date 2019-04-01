package nl.avans.kinoplex.presentation.viewholders;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import nl.avans.kinoplex.R;

import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;

public class ReviewViewHolder extends AbstractViewHolder {

    private TextView reviewUser;
    private RatingBar reviewRating;
    private TextView reviewContent;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);



        Log.d(MAINMOVIEVH_TAG, "ReviewViewHolder was created");

        reviewUser = itemView.findViewById(R.id.review_id);
        reviewContent = itemView.findViewById(R.id.review_content);
        reviewRating = itemView.findViewById(R.id.review_rating);
        reviewRating.setIsIndicator(true);
    }

    public TextView getReviewUser() {
        return reviewUser;
    }

    public RatingBar getReviewRating() {
        return reviewRating;
    }

    public TextView getReviewContent() {
        return reviewContent;
    }
}
