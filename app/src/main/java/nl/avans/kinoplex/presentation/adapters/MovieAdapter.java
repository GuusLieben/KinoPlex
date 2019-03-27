package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {
  private List<DocumentSnapshot> dataSet;

  public MovieAdapter(ArrayList<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {}

  @Override
  public int getItemCount() {
    return 0;
  }
}
