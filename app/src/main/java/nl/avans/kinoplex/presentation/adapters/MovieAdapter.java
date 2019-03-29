package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

public class MovieAdapter extends AbstractAdapter<MovieViewHolder> {

  public MovieAdapter(List<DomainObject> dataSet) {
    super(dataSet);
  }


  @NonNull
  @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
    DomainObject object = getDataSet().get(i);
    movieViewHolder.bind(object);
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

}
