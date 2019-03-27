package nl.avans.kinoplex.presentation.adapters;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface AdapterInterface {

  void updateDataSet(List<DocumentSnapshot> dataSet);

  void addToDataSet(DocumentSnapshot documentSnapshot);
}
