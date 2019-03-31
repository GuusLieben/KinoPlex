package nl.avans.kinoplex.presentation.adapters;



import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;


public class MainListAdapter extends AbstractAdapter<MainListViewHolder> {

  private Context context;

  public MainListAdapter(List<DomainObject> dataSet, Context context) {

    super(dataSet);
    this.context = context;
  }

  @NonNull
  @Override
  public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    Context context = viewGroup.getContext();
    int layoutIdForMovieList = R.layout.movie_list;
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(layoutIdForMovieList, viewGroup, false);

    return new MainListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MainListViewHolder mainListViewHolder, int i) {
    MovieList list = (MovieList) getDataSet().get(i);
    mainListViewHolder.bind(list);

  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

}
