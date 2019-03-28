package nl.avans.kinoplex.business.firestoreutils;

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

import static nl.avans.kinoplex.business.firestoreutils.FirestoreUtils.addToAdapter;
import static nl.avans.kinoplex.business.firestoreutils.FirestoreUtils.updateAdapter;

public class FirestoreRead
    extends AsyncTask<Pair<String, RecyclerView.Adapter>, Void, List<DocumentSnapshot>> {

  private Object documentId;

  public FirestoreRead(Object documentId) {
    this.documentId = documentId;
  }

  @SafeVarargs
  @Override
  protected final List<DocumentSnapshot> doInBackground(
      Pair<String, RecyclerView.Adapter>... data) {
    // Get the collection name as passed to the Utility by the calling method (Constants : lists,
    // movies, genres)
    String collection = data[0].first;

    // Set up Firestore instance
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
    firestore.setFirestoreSettings(settings);
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // If only the collection is requested
    if (documentId == null) {
      // Collect the collection directly
      Task<QuerySnapshot> task = db.collection(collection).get();
      task.addOnCompleteListener(
          querySnapshotTask -> {
            // Swap the entire adapter once results have been gathered
            QuerySnapshot snapshot = querySnapshotTask.getResult();
            updateAdapter(snapshot.getDocuments(), data[0].second);
          });

      // If a specific document is requested
    } else {
      // Collect the specific document
      Task<DocumentSnapshot> task = db.collection(collection).document(documentId.toString()).get();
      task.addOnCompleteListener(
          documentSnapshotTask -> {
            // Add to the adapter once results have been gathered
            DocumentSnapshot snapshot = documentSnapshotTask.getResult();
            addToAdapter(snapshot, data[0].second);
          });
    }
    // Never return null
    return new ArrayList<>();
  }
}
