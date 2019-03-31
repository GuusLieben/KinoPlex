package nl.avans.kinoplex.presentation.viewholders;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.activities.DetailActivity;

import static nl.avans.kinoplex.domain.Constants.MAINMOVIEVH_TAG;

public class MovieViewHolder extends AbstractViewHolder implements View.OnClickListener {

    private ImageView moviePoster;
    private TextView movieTitle;

    private Movie movie;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.image_view_movie_poster);
        movieTitle = itemView.findViewById(R.id.movie_title);
        itemView.setOnClickListener(this);
    }

    public ImageView getMoviePoster() {
        return moviePoster;
    }

    public TextView getMovieTitle() {
        return movieTitle;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onClick(View v) {
        Log.d(MAINMOVIEVH_TAG, "User clicked on MainMovieViewHolder");

        Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
        DataMigration.getFactory().getMovieDao().readIntoIntent(detailIntent, v.getContext(), movie.getId());
    }
}
