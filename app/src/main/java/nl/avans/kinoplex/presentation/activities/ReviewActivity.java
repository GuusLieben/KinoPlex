package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class ReviewActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private ReviewAdapter adapter;
    private RecyclerView reviewRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_recyclelist);

        String movieJson = getIntent().getStringExtra("movieJson");
        Movie movie = new Gson().fromJson(movieJson, Movie.class);

        setTitle("Reviews for '" + movie.getTitle() + '\'');

        DrawerLayout drawerLayout = findViewById(R.id.search_drawerlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        reviewRecyclerView = findViewById(R.id.reviewlist_recyclerview);
        adapter = new ReviewAdapter(new ArrayList<>());

        DataMigration.getFactory()
                .getReviewDao(Integer.parseInt(movie.getId()))
                .readIntoAdapter(adapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
