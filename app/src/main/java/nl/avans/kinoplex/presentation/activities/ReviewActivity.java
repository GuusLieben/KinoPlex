package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.presentation.adapters.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity {

  private ReviewAdapter adapter;
  private RecyclerView reviewRecyclerView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.review_recyclelist);

    String movieJson = getIntent().getStringExtra("movieJson");
    Movie movie = new Gson().fromJson(movieJson, Movie.class);

    reviewRecyclerView = findViewById(R.id.reviewlist_recyclerview);
    adapter = new ReviewAdapter(new ArrayList<>());

    DataMigration.getFactory()
        .getReviewDao(Integer.parseInt(movie.getId()))
        .readIntoAdapter(adapter);

    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    reviewRecyclerView.setAdapter(adapter);
  }
}
