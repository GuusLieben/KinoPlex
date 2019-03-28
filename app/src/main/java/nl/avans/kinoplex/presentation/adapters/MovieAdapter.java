package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

public class MovieAdapter extends AbstractAdapter<MovieViewHolder> {

  public MovieAdapter(List<DocumentSnapshot> dataSet) {
    super(dataSet);
  }


  @NonNull
  @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

  }

}
