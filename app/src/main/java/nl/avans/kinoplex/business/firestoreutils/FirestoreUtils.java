package nl.avans.kinoplex.business.firestoreutils;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;

public class FirestoreUtils {

  private Object documentId;

  public FirestoreUtils(Object documentId) {
    this.documentId = documentId;
  }

  public void writeToFirestore(String collection, DomainObject obj) {
    FirestoreWrite firestoreWrite = new FirestoreWrite();
    firestoreWrite.execute(collection, obj.storeToMap());
  }

  public void readIntoAdapter(Pair<String, RecyclerView.Adapter> adapterPair) {
    FirestoreRead firestoreRead = new FirestoreRead(documentId);
    //noinspection unchecked
    firestoreRead.execute(adapterPair);
  }

  public static void addToAdapter(DocumentSnapshot documentSnapshot, RecyclerView.Adapter adapter) {
    // Add to the dataset, use abstracts
    ((AbstractAdapter<AbstractViewHolder>) adapter).addToDataSet(documentSnapshot);
  }

  public static void updateAdapter(List<DocumentSnapshot> snapshot, RecyclerView.Adapter adapter) {
    // Update the dataset, use abstracts
    ((AbstractAdapter<AbstractViewHolder>) adapter).updateDataSet(snapshot);
  }
}
