package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.business.JsonUtilsTask;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.Movie;

public class TMDbMovieDao implements DaoObject {

    private int movieId;

    public TMDbMovieDao(int movieId) {
        this.movieId = movieId;
    }

    public TMDbMovieDao() {
    }

    @Override
    public boolean create(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        if (id instanceof Movie) {
            Uri uri = Uri.parse(Constants.MOVIE_API_URL + ((Movie) id).getId())
                    .buildUpon()
                    .appendQueryParameter("api_key", Constants.API_KEY)
                    .appendQueryParameter("language", "en-US")
                    .build();
            try {
                JSONObject result = new JsonUtilsTask().execute(uri).get();
                String language = result.getString("original_language");
                String tagline = result.getString("tagline");
                int runtime = result.getInt("runtime");
                JSONArray genres = result.getJSONArray("genres");
                double rating = result.getDouble("vote_average");
                String[] genreIds = new String[genres.length()];

                if (genreIds.length > 0)
                    for (int i = 0; i < genres.length(); i++) {
                        JSONObject genre = (JSONObject) genres.get(i);
                        genreIds[i] = String.valueOf(genre.getInt("id"));
                    }
                Movie movie = (Movie) id;
                movie.setLanguage(language);
                movie.setTag(tagline);
                movie.setRuntime(runtime);
                movie.setGenres(genreIds);
                movie.setRating(rating);

                DataMigration.getFactory().getMovieDao().create(movie);

                String movieJson = new Gson().toJson(movie);
                intent.putExtra("movieJson", movieJson);

                context.startActivity(intent);

            } catch (JSONException ex) {
                ex.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
//      Uri movieUri = Uri.parse("").buildUpon().appendQueryParameter();
    }

    @Override
    public boolean update(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Object o) {
        throw new UnsupportedOperationException();
    }
}
