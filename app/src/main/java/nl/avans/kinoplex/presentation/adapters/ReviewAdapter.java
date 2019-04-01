package nl.avans.kinoplex.presentation.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.AppReview;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.viewholders.ReviewViewHolder;

public class ReviewAdapter extends AbstractAdapter<ReviewViewHolder>{

    public ReviewAdapter(List<DomainObject> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listRow = inflater.inflate(R.layout.reviewapp_recyclelist_row, viewGroup, false);
        return new ReviewViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        AppReview review = (AppReview) getDataSet().get(i);
        reviewViewHolder.getReviewUser().setText(review.getUserId());
        reviewViewHolder.getReviewContent().setText(review.getContent());
        reviewViewHolder.getReviewRating().setNumStars(review.getRating());
    }

    @Override
    public int getItemCount() {
        return getDataSet().size();
    }
}
