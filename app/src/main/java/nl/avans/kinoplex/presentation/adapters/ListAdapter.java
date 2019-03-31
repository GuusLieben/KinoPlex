package nl.avans.kinoplex.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.viewholders.MovieViewHolder;

import static nl.avans.kinoplex.presentation.adapters.SearchAdapter.getYear;

public class ListAdapter extends AbstractAdapter<MovieViewHolder> {
    private Context context;
    public ListAdapter(List<DomainObject> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        int layoutID = R.layout.movie_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutID, viewGroup, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int i) {
        Movie movie = (Movie) getDataSet().get(i);
        ImageView imageView = viewHolder.itemView.findViewById(R.id.image_view_movie_poster);
        TextView releaseYear = viewHolder.itemView.findViewById(R.id.movie_year);
        TextView genre = viewHolder.itemView.findViewById(R.id.movie_genre);
        RatingBar ratingBar = viewHolder.itemView.findViewById(R.id.movie_rating);
        Glide.with(viewHolder.itemView.getContext()).load(movie.getPosterPath()).into(imageView); // sets the poster of the current movie in the recyclerview
        //genre.setText(movie.getGenres()[0]);
        viewHolder.getMovieTitle().setText(movie.getTitle()); // sets the title of the movie in the recyclerview
        releaseYear.setText(String.valueOf(getYear(movie.getReleaseDate()))); // sets the releaseyear of the movie in the recyclerview
        if (movie.getRating() != null)
            ratingBar.setRating(movie.getRating().floatValue()); // sets rating of the movie
        else ratingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return getDataSet().size();
    }
}
