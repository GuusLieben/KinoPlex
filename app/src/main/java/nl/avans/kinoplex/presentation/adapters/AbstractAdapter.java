package nl.avans.kinoplex.presentation.adapters;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public abstract class AbstractAdapter extends RecyclerView.Adapter {
  private List<DocumentSnapshot> dataSet;

  public AbstractAdapter(List<DocumentSnapshot> dataSet) {
    this.dataSet = dataSet;
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
    return dataSet.size();
  }

  List<DocumentSnapshot> getDataSet() {
      return dataSet;
  }
}
