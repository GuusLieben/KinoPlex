package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.viewholders.MainMovieViewHolder;

public class MainMovieAdapter extends AbstractAdapter<MainMovieViewHolder> {

  public MainMovieAdapter(List<DomainObject> dataSet) {
    super(dataSet);
  }


  @NonNull
  @Override
  public MainMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MainMovieViewHolder movieViewHolder, int i) {
    DomainObject object = getDataSet().get(i);
    movieViewHolder.bind(object);
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

}
