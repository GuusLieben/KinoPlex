package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;

public class MainListAdapter extends AbstractAdapter<MainListViewHolder> {

  public MainListAdapter(List<DocumentSnapshot> dataSet) {
    super(dataSet);
  }

  @NonNull
  @Override
  public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MainListViewHolder mainListViewHolder, int i) {
    DocumentSnapshot documentSnapshot = getDataSet().get(i);
    String name = documentSnapshot.getString("name");
    Object[] movieIds = (Object[]) documentSnapshot.get("movies");
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }
}
