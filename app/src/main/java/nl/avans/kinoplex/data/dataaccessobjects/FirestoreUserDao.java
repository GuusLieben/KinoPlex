package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;

public class FirestoreUserDao implements DaoObject<Pair> {

  private FirebaseFirestore db;

  public FirestoreUserDao() {
    this.db = FirestoreUtils.getInstance();
  }

  @Override
  public boolean create(Pair pair) {
    String id = db.collection(Constants.COL_USERS).document().getId();
    // TODO : Add a line to store UserID to shared preferences
    HashMap<String, Object> userData =
        new HashMap<String, Object>() {
          {
            put("fullname", pair.first);
            put("username", pair.first.toString().replace(" ", "").toLowerCase());
            put("password", pair.second);
          }
        };
    db.collection(Constants.COL_USERS).document(id).set(userData);
    return true;
  }

  @Override
  public void readIntoAdapter(RecyclerView.Adapter adapter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void readIntoIntent(Intent intent, Context context, Object id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void readAll(RecyclerView.Adapter adapter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean update(Pair pair) {
    // ToDO : Add a line to collect UserID from shared preferences
    return true;
  }

  @Override
  public boolean delete(Pair pair) {
    throw new UnsupportedOperationException();
  }
}
