package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

import static nl.avans.kinoplex.domain.Constants.FIRESTORELISTDAO_TAG;

public class FirestoreListDao implements DaoObject<MovieList> {

    private FirebaseFirestore db;

    public FirestoreListDao() {
        db = FirestoreUtils.getInstance();
    }

    public MovieList createListForUser(MovieList movieList) {
        String collectionId = db.collection(Constants.COL_LISTS).document().getId();
        movieList.setDbId(collectionId);
        db.collection(Constants.COL_LISTS).document(collectionId).set(movieList.storeToMap());
        return movieList;
    }

    public void readCollectionsForCurrentUserToAdapter(RecyclerView.Adapter adapter) {
        db.collection(Constants.COL_LISTS).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                if (documentSnapshot.getString("user_id").equalsIgnoreCase(Constants.pref.getString("userId", "-1")) || documentSnapshot.getString("user_id").equals("-1")) {
                    String userId = documentSnapshot.getString("user_id");
                    String name = documentSnapshot.getString("name");
                    MovieList list = new MovieList(name, userId);
                    list.setDbId(documentSnapshot.getId());
                    for (Object movie : (List<Object>) documentSnapshot.get("movies")) {
                        int movieId = Integer.parseInt(String.valueOf(movie));
                        ((FirestoreMovieDao) DataMigration.getFactory().getMovieDao(movieId)).readIntoList(list);
                    }
                    ((AbstractAdapter) adapter).addToDataSet(list);
                }
            }
        });
    }

    @Override
    public boolean create(MovieList movieList) {
        DocumentReference ref = db.collection(Constants.COL_LISTS).document();
        Log.d(FIRESTORELISTDAO_TAG, "Attempting to write to Firestore with id " + movieList.getDbId() + " / " + movieList.getId());
        db.collection(Constants.COL_LISTS)
                .document(ref.getId())
                .set(movieList.storeToMap())
                .addOnSuccessListener(aVoid -> Log.d(FIRESTORELISTDAO_TAG, "Successfully wrote List to Firestore"))
                .addOnFailureListener(e -> Log.w(FIRESTORELISTDAO_TAG, "Error writing document", e));
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        Task<QuerySnapshot> task = db.collection(Constants.COL_LISTS).get();
        Log.d(FIRESTORELISTDAO_TAG, "Attempting to read from Firebase");
        task.addOnCompleteListener(
                querySnapshotTask -> {
                    Log.d(FIRESTORELISTDAO_TAG, "Successfully read lists from Firebase");
                    QuerySnapshot snapshot = querySnapshotTask.getResult();
                    List<DomainObject> movieLists = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        String name = documentSnapshot.getString("name");
                        Log.d(FIRESTORELISTDAO_TAG, "Collected list with name " + name);
                        String userId = Objects.requireNonNull(documentSnapshot.get("user_id")).toString();
                        if (userId.equalsIgnoreCase("-1") || userId.equalsIgnoreCase(Constants.pref.getString("userId", "-1"))) {
                            MovieList list = new MovieList(name, userId);

                            list.setDbId(documentSnapshot.getId());
                            List<Object> movieIds = (List<Object>) documentSnapshot.get("movies");

                            ArrayList<String> registeredIds = new ArrayList<>();
                            for (Object movieId : Objects.requireNonNull(movieIds)) {
                                if (!registeredIds.contains(String.valueOf(movieId))) {
                                    ((FirestoreMovieDao)
                                            DataMigration.getFactory().getMovieDao(Integer.parseInt(String.valueOf(movieId))))
                                            .readIntoList(list);
                                    registeredIds.add(String.valueOf(movieId));
                                }
                            }
                            movieLists.add(list);
                        }
                    }
                    Log.d(FIRESTORELISTDAO_TAG, "Updating DataSet");
                    ((AbstractAdapter) adapter).updateDataSet(movieLists);
                });
    }

    public void addMovieToList(MovieList list, int movieId) {
        Map<String, Object> listMap = list.storeToMap();
        ArrayList<Object> movies = (ArrayList<Object>) listMap.get("movies");
        Log.d(FIRESTORELISTDAO_TAG, "Storing movies to list");
        if (movies == null) {
            movies = new ArrayList<>();
            movies.add(String.valueOf(movieId));
        } else {
            ArrayList<Object> newMovies = new ArrayList<>(movies);
            newMovies.add(movieId);
            movies = newMovies;
        }
        listMap.put("movies", movies);
        String id = list.getDbId();
        if (id == null) {
            id = db.collection(Constants.COL_LISTS).document().getId();
            list.setDbId(id);
        }

        Log.d(FIRESTORELISTDAO_TAG, "Attempting to write to Firestore with id " + list.getId() + " / " + list.getDbId());
        db.collection(Constants.COL_LISTS).document(list.getDbId()).set(listMap);
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
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
        Log.d(FIRESTORELISTDAO_TAG, "Attempting to write to Firestore");
        db.collection(Constants.COL_LISTS)
                .document(movieList.getDbId())
                .set(movieList.storeToMap())
                .addOnSuccessListener(aVoid -> Log.d(FIRESTORELISTDAO_TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(FIRESTORELISTDAO_TAG, "Error writing document", e));
        return true;
    }

    @Override
    public boolean delete(MovieList movieList) {
        if (movieList.getDbId() == null) {
            DocumentReference ref = db.collection(Constants.COL_LISTS).document();
            movieList.setDbId(ref.getId());
        }
        Log.d(FIRESTORELISTDAO_TAG, "Attempting to delete list from Firestore");
        db.collection(Constants.COL_LISTS)
                .document(movieList.getDbId())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(FIRESTORELISTDAO_TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(FIRESTORELISTDAO_TAG, "Error writing document", e));
        return true;
    }
}
