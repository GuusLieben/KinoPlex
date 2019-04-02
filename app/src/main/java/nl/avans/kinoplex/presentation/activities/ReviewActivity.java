package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

        Toolbar toolbar = findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviews for '" + movie.getTitle() + '\'');
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reviewRecyclerView = findViewById(R.id.reviewlist_recyclerview);
        adapter = new ReviewAdapter(new ArrayList<>());

        DataMigration.getFactory()
                .getReviewDao(Integer.parseInt(movie.getId()))
                .readIntoAdapter(adapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
