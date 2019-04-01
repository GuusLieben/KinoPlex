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
import nl.avans.kinoplex.domain.TMDbReview;
import nl.avans.kinoplex.presentation.viewholders.ReviewViewHolder;

public class ReviewAdapter extends AbstractAdapter<ReviewViewHolder> {

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
    Object review = getDataSet().get(i);
    if (review instanceof TMDbReview) {
      TMDbReview tmDbReview = (TMDbReview) review;
      reviewViewHolder.getReviewUser().setText(tmDbReview.getAuthor());
      reviewViewHolder.getReviewContent().setText(tmDbReview.getContent());
    } else if (review instanceof AppReview) {
      System.out.println("Hoe dan");
      // TODO : Doen we dit? Misschien..
    }
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }
}
