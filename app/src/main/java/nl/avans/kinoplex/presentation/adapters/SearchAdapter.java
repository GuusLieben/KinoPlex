package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;


public class SearchAdapter extends AbstractAdapter<MovieViewHolder> implements Filterable {

    public SearchAdapter(List<DocumentSnapshot> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row,
                viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
        DocumentSnapshot currentMovie = getDataSet().get(position);

        // viewHolder.moviePoster.setImageResource(currentMovie.getPosterPath()); // glide or picasso
        viewHolder.getMovieTitle().setText(currentMovie.getString("title"));
    }

    @Override
    public int getItemCount() {
        return getDataSet().size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DocumentSnapshot> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(getDataSet());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DocumentSnapshot snapshot : getDataSet()) {
                    if (snapshot.getString("title").toLowerCase().contains(filterPattern)) {
                        filteredList.add(snapshot);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            updateDataSet((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
