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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;

import static android.content.ContentValues.TAG;

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
                    for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                        String name = documentSnapshot.getString("name");
                        String userId = Objects.requireNonNull(documentSnapshot.get("user_id")).toString();
                        MovieList list = new MovieList(name, Integer.parseInt(userId));

                        list.setDbId(documentSnapshot.getId());
                        List<Object> movieIds = (List<Object>) documentSnapshot.get("movies");

                        for (Object movieId : Objects.requireNonNull(movieIds)) {
                            ((FirestoreMovieDao)
                                    DataMigration.getFactory().getMovieDao(Integer.parseInt(String.valueOf(movieId))))
                                    .readIntoList(list);
                            System.out.println(movieId);
                        }
                        movieLists.add(list);
                    }
                    ((AbstractAdapter) adapter).updateDataSet(movieLists);
                });
    }

    public void addMovieToList(MovieList list, int movieId) {
        Map<String, Object> listMap = list.storeToMap();
        ArrayList<Object> movies = (ArrayList<Object>) listMap.get("movies");
        System.out.println(movies);
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
        db.collection(Constants.COL_LISTS).document(list.getDbId()).set(listMap);
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
        // TODO : Make this work
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
