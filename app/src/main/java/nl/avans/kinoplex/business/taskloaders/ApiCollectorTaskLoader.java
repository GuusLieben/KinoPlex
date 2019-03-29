package nl.avans.kinoplex.business.taskloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;

public class ApiCollectorTaskLoader extends AsyncTaskLoader<List<DomainObject>> {
  public ApiCollectorTaskLoader(@NonNull Context context) {
    super(context);
  }

  @Nullable
  @Override
  public List<DomainObject> loadInBackground() {
    return null;
  }
}
