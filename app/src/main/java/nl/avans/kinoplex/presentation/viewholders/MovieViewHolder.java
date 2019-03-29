package nl.avans.kinoplex.presentation.viewholders;

import android.media.Rating;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;

public class MovieViewHolder extends AbstractViewHolder{

    private ImageView moviePoster;
    private TextView movieTitle;
    private RatingBar movieRating;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.image_view_movie_poster);
        movieTitle = itemView.findViewById(R.id.movie_title);
        movieRating = itemView.findViewById(R.id.rating_bar_movie_poster);
        movieRating.setIsIndicator(true);
    }

    public ImageView getMoviePoster() {
        return moviePoster;
    }

    public TextView getMovieTitle() {
        return movieTitle;
    }

    public void bind(DomainObject obj) {
        Movie movie = (Movie) obj;
        movieTitle.setText(movie.getTitle());
        movieRating.setNumStars(movie.getRating());
        Glide.with(movieTitle)
                .load(movie.getPosterPath())
                .into(moviePoster);
    }
}
