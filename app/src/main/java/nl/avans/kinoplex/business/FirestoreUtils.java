package nl.avans.kinoplex.business;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.presentation.adapters.AdapterInterface;

public class FirestoreUtils
    extends AsyncTask<Pair<String, RecyclerView.Adapter>, Void, List<DocumentSnapshot>> {

  private Object documentId;

  public FirestoreUtils(Object documentId) {
    this.documentId = documentId;
  }

  @Override
  protected List<DocumentSnapshot> doInBackground(Pair<String, RecyclerView.Adapter>... data) {
    String collection = data[0].first;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
    firestore.setFirestoreSettings(settings);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    System.out.println("Starting collect");

    // If only the collection is requested
    if (documentId == null) {
      Task<QuerySnapshot> task = db.collection(collection).get();
      task.addOnCompleteListener(
          querySnapshotTask -> {
            QuerySnapshot snapshot = querySnapshotTask.getResult();
            updateAdapter(snapshot.getDocuments(), data[0].second);
          });

      // If a specific document is requested
    } else {
      Task<DocumentSnapshot> task = db.collection(collection).document(documentId.toString()).get();
      task.addOnCompleteListener(
          documentSnapshotTask -> {
            DocumentSnapshot snapshot = documentSnapshotTask.getResult();
            addToAdapter(snapshot, data[0].second);
          });
    }
    return new ArrayList<>();
  }

  private void addToAdapter(DocumentSnapshot documentSnapshot, RecyclerView.Adapter adapter) {
    ((AdapterInterface) adapter).addToDataSet(documentSnapshot);
  }

  private void updateAdapter(List<DocumentSnapshot> snapshot, RecyclerView.Adapter adapter) {
    ((AdapterInterface) adapter).updateDataSet(snapshot);
  }
}
