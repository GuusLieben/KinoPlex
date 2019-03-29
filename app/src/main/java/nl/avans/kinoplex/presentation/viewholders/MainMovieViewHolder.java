package nl.avans.kinoplex.presentation.viewholders;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.activities.DetailActivity;
import nl.avans.kinoplex.presentation.activities.MainActivity;

import static nl.avans.kinoplex.domain.Constants.INTENT_EXTRA_MOVIEID;
import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;

public class MainMovieViewHolder extends AbstractViewHolder implements View.OnClickListener {
    
    /*
        ViewHolder for a movie in MainActivity
        The ViewHolder holds the poster, the title and the star rating
        When the user clicks on this ViewHolder, the movie's ID will be passed to DetailActivity
     */

    private ImageView moviePoster;
    private TextView movieTitle;
    private RatingBar movieRating;

    Context context;

    private Movie movie;

    public MainMovieViewHolder(@NonNull View itemView) {
        super(itemView);

        this.context = itemView.getContext();

        Log.d(MAINMOVIEVH_TAG, "MainMovieViewHolder was created");

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
        movie = (Movie) obj;
        movieTitle.setText(movie.getTitle());
        movieRating.setNumStars(movie.getRating());
        Glide.with(movieTitle)
                .load(movie.getPosterPath())
                .into(moviePoster);
    }

    @Override
    public void onClick(View v) {
        Log.d(MAINMOVIEVH_TAG, "User clicked on MainMovieViewHolder");

        Intent detailIntent = new Intent(context, DetailActivity.class);
        DataMigration.getFactory().getMovieDao().readIntoIntent(detailIntent, context, "550");

    }
}
