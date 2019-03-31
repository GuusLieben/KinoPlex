package nl.avans.kinoplex.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

public class SearchAdapter extends AbstractAdapter<MovieViewHolder> implements Filterable {
    private List<DomainObject> listFull;
    private List<DomainObject> list;

    public SearchAdapter(List<DomainObject> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row, viewGroup, false);
        this.list = new ArrayList<>(getDataSet());
        listFull = new ArrayList<>(list);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int position) {
        Movie movie = (Movie) list.get(position);
        ImageView imageView = viewHolder.itemView.findViewById(R.id.image_view_movie_poster);
        TextView releaseYear = viewHolder.itemView.findViewById(R.id.movie_year);
        TextView genre = viewHolder.itemView.findViewById(R.id.movie_genre);
        RatingBar ratingBar = viewHolder.itemView.findViewById(R.id.movie_rating);
        Glide.with(viewHolder.itemView.getContext()).load(movie.getPosterPath()).into(imageView); // sets the poster of the current movie in the recyclerview
        //genre.setText(movie.getGenres()[0]);
        viewHolder.getMovieTitle().setText(movie.getTitle()); // sets the title of the movie in the recyclerview
        releaseYear.setText(String.valueOf(getYear(movie.getReleaseDate()))); // sets the releaseyear of the movie in the recyclerview
        ratingBar.setRating(movie.getRating().floatValue()); // sets rating of the movie

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


                    if (constraint == null || constraint.length() == 0) { // checks if the searchview is empty, if so, all data will be shown
                        filteredList.addAll(listFull);
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim(); // otherwise the chosen filter will be applied

                        for (DomainObject movie : listFull) {
                            if (Objects.requireNonNull(((Movie) movie).getTitle())
                                    .toLowerCase()
                                    .contains(filterPattern)) {
                                filteredList.add(movie);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    list.clear();
                    list.addAll((List) filterResults.values);
                    updateDataSet((List<DomainObject>) filterResults.values); // update the data with the filteredresults
                    notifyDataSetChanged();
                }
            };

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
