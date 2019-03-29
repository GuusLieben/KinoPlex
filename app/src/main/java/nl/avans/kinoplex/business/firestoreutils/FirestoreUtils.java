package nl.avans.kinoplex.business.firestoreutils;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;

public class FirestoreUtils {

  public static FirebaseFirestore getInstance() {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();

    firestore.setFirestoreSettings(settings);
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    return db;
  }

  public static void addToAdapter(DocumentSnapshot documentSnapshot, RecyclerView.Adapter adapter) {
    // Add to the dataset, use abstracts
    ((AbstractAdapter<AbstractViewHolder>) adapter).addToDataSet(documentSnapshot);
  }

  public static void updateAdapter(List<DomainObject> snapshot, RecyclerView.Adapter adapter) {
    // Update the dataset, use abstracts
    ((AbstractAdapter<AbstractViewHolder>) adapter).updateDataSet(snapshot);
  }
}
