package nl.avans.kinoplex.presentation.adapters;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;

public abstract class AbstractAdapter<M extends AbstractViewHolder> extends RecyclerView.Adapter<M> {
  private List<DocumentSnapshot> dataSet;

  public AbstractAdapter(List<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
  }

  // Replace the entire dataset (used for Collection collection)
  public void updateDataSet(List<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
    notifyDataSetChanged();
  }

  // Add to the dataset, but don't replace it (used for Document collection)
  public void addToDataSet(DocumentSnapshot documentSnapshot) {
    this.dataSet.add(documentSnapshot);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  List<DocumentSnapshot> getDataSet() {
      return dataSet;
  }
}
