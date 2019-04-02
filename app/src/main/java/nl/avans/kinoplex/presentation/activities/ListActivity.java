package nl.avans.kinoplex.presentation.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.business.PosterPicker;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.ListAdapter;

public class ListActivity extends AppCompatActivity {
    private MovieList movieList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("ListActivity");

        ImageView background = findViewById(R.id.iv_background_poster);
        Glide.with(this).load(PosterPicker.getRandomPosterID()).into(background);

        if (getIntent().getExtras() == null) {
            return;
        }

        // Get the provided movie data, logging the response
        String JSON = getIntent().getExtras().getString(Constants.INTENT_EXTRA_MOVIELIST);
        String jsonObj = getIntent().getStringExtra(Constants.INTENT_EXTRA_MOVIELIST);
        Log.d(Constants.DETAILACT_TAG, JSON);

        movieList = new Gson().fromJson(jsonObj, MovieList.class);

        Toolbar toolbar = findViewById(R.id.toolbar_list_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle( movieList.getName() );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_detail_list);
        adapter = new ListAdapter(movieList.getDomainMovieList());
        for ( Movie m : movieList.getMovieList() ) {
            Log.d("Movie", "title ---------------------------------------------------------------> " + m.getTitle());
        }
        Log.d(Constants.MOVIELIST_TAG, "movielist -> " + movieList.getMovieList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_filter_menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.list_filter_menu_item:
                DialogBuilder.createFilterDialog(this, getFilter());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Filter getFilter() {
        return ((ListAdapter)adapter).getFilter();
    }
}
