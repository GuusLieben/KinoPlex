package nl.avans.kinoplex.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

public class MainListAdapter extends AbstractAdapter<MainListViewHolder> {

  private Context context;

  public MainListAdapter(List<DomainObject> dataSet, Context context) {
    super(dataSet);
    this.context = context;
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
    List<DomainObject> domainMovies = new ArrayList<>(movies);

    AbstractAdapter<MovieViewHolder> adapter = new MovieAdapter(domainMovies);
    RecyclerView recyclerView = ((Activity) context).findViewById(R.id.recyclerview_movie_filter);

    recyclerView.setAdapter(adapter);
    // Adapter shit
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }
}
