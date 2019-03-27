package nl.avans.kinoplex.business.taskloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class ApiCollectorTaskLoader extends AsyncTaskLoader {
  public ApiCollectorTaskLoader(@NonNull Context context) {
    super(context);
  }

  @Nullable
  @Override
  public Object loadInBackground() {
    return null;
  }
}
