package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import nl.avans.kinoplex.business.firestoreutils.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;

import static android.content.ContentValues.TAG;
import static nl.avans.kinoplex.business.firestoreutils.FirestoreUtils.updateAdapter;

public class FirestoreListDao implements DaoObject<MovieList> {

  private FirebaseFirestore db;

  public FirestoreListDao() {
    db = FirestoreUtils.getInstance();
  }

  @Override
  public boolean create(MovieList movieList) {
    DocumentReference ref = db.collection(Constants.COL_LISTS).document();
    db.collection(Constants.COL_LISTS)
        .document(ref.getId())
        .set(movieList.storeToMap())
        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
        .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    return true;
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {
    Task<QuerySnapshot> task = db.collection(Constants.COL_LISTS).get();
    task.addOnCompleteListener(
        querySnapshotTask -> {
          QuerySnapshot snapshot = querySnapshotTask.getResult();
          List<DomainObject> movieLists = new ArrayList<>();
          for (Iterator<QueryDocumentSnapshot> iterator = snapshot.iterator();
              iterator.hasNext(); ) {
            QueryDocumentSnapshot documentSnapshot = iterator.next();

            String name = documentSnapshot.getString("name");
            int userId =
                Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString("user_id")));
            MovieList list = new MovieList(name, userId);

            list.setDbId(documentSnapshot.getId());
            Map<String, Object> movieIds = (Map<String, Object>) documentSnapshot.get("movies");

            for (Map.Entry<String, Object> movieId : movieIds.entrySet())
              ((FirestoreMovieDao)
                      DataMigration.getFactory().getMovieDao((Integer) movieId.getValue()))
                      .readIntoList(list);
            movieLists.add(list);
          }
          updateAdapter(movieLists, adapter);
        });
  }

  @Override
  public void readIntoIntent(Intent intent, Context context) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void readAll(RecyclerView.Adapter adapter) {
    readIntoAdapter(adapter);
  }

  @Override
  public boolean update(MovieList movieList) {
    if (movieList.getDbId() == null) {
      DocumentReference ref = db.collection(Constants.COL_LISTS).document();
      movieList.setDbId(ref.getId());
    }
    db.collection(Constants.COL_LISTS)
        .document(movieList.getDbId())
        .set(movieList.storeToMap())
        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
        .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    return true;
  }

  @Override
  public boolean delete(MovieList movieList) {
    if (movieList.getDbId() == null) {
      DocumentReference ref = db.collection(Constants.COL_LISTS).document();
      movieList.setDbId(ref.getId());
    }
    db.collection(Constants.COL_LISTS)
        .document(movieList.getDbId())
        .delete()
        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
        .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    return true;
  }
}
