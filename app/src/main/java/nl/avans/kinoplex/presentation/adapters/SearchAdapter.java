package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.viewholders.MainMovieViewHolder;

public class SearchAdapter extends AbstractAdapter<MainMovieViewHolder> implements Filterable {

  public SearchAdapter(List<DomainObject> dataSet) {
    super(dataSet);
  }

  @NonNull
  @Override
  public MainMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
    View v =
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row, viewGroup, false);
    return new MainMovieViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull MainMovieViewHolder viewHolder, int position) {
    DomainObject currentMovie = (Movie) getDataSet().get(position);

    // viewHolder.moviePoster.setImageResource(currentMovie.getPosterPath()); // glide or picasso
    viewHolder.getMovieTitle().setText(((Movie) currentMovie).getTitle());
  }

  @Override
  public int getItemCount() {
    return getDataSet().size();
  }

  @Override
  public Filter getFilter() {
    return movieFilter;
  }

  private Filter movieFilter =
      new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          List<DomainObject> filteredList = new ArrayList<>();

          if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(getDataSet());
          } else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for (DomainObject object : getDataSet()) {
              if (Objects.requireNonNull(((Movie) object).getTitle())
                  .toLowerCase()
                  .contains(filterPattern)) {
                filteredList.add(object);
              }
            }
          }
          FilterResults results = new FilterResults();
          results.values = filteredList;

          return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
          updateDataSet((List<DomainObject>) filterResults.values);
          notifyDataSetChanged();
        }
      };
}
