package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.domain.Movie;

public class SearchAdapter extends RecyclerView.Adapter implements Filterable {
    private List<Movie> movieList;
    private List<Movie> movieListFull;

    public SearchAdapter(List<Movie> movieList) {
        this.movieList = movieList;
        movieListFull = new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movieListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Movie movie : movieListFull) {
                    if (movie.getTitle().toLowerCase().contains(filterPattern)) {
                        movieListFull.add(movie);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            movieList.clear();
            movieList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
