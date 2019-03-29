package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;

public class MainListAdapter extends AbstractAdapter<MainListViewHolder> {

  public MainListAdapter(List<DomainObject> dataSet) {
    super(dataSet);
  }

  @NonNull
  @Override
  public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull MainListViewHolder mainListViewHolder, int i) {
    MovieList list = (MovieList) getDataSet().get(i);
    String name = list.getName();
    List<Movie> movies = list.getMovieList();
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }
}
