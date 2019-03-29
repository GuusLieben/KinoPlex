package nl.avans.kinoplex.business.firestoreutils;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirestoreUtils {

  public static FirebaseFirestore getInstance() {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings =
        new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
    firestore.setFirestoreSettings(settings);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db;
  }
}
