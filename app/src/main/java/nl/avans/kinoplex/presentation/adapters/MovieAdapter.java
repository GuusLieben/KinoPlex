package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;

public class MovieAdapter extends AbstractAdapter<AbstractViewHolder> {

  public MovieAdapter(List<DocumentSnapshot> dataSet) {
    super(dataSet);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {}
}
