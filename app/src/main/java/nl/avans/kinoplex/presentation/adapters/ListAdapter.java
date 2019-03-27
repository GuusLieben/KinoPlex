package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter implements AdapterInterface {

  private List<DocumentSnapshot> dataSet;

  public ListAdapter(List<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    DocumentSnapshot documentSnapshot = dataSet.get(i);
    String name = documentSnapshot.getString("name");
    Object[] movieIds = (Object[]) documentSnapshot.get("movies");
    // Do something with it..
  }

  public void updateDataSet(List<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
    notifyDataSetChanged();
  }

  public void addToDataSet(DocumentSnapshot documentSnapshot) {
    this.dataSet.add(documentSnapshot);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return 0;
  }
}
