package nl.avans.kinoplex.business.firestoreutils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

class FirestoreWrite extends AsyncTask<Object, Void, Boolean> {

  @Override
  protected Boolean doInBackground(Object... params) {
    String collection = params[0].toString();
    HashMap<String, Object> document = (HashMap<String, Object>) params[1];

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
    firestore.setFirestoreSettings(settings);
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    db.collection(collection)
        .document("LA")
        .set(document)
        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
        .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));

    return true;
  }
}
