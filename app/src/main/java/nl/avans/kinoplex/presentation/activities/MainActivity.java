package nl.avans.kinoplex.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.presentation.adapters.MainListAdapter;

public class MainActivity extends AppCompatActivity {


    private MainListAdapter parentAdapter;
    RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.home));

        // set the recyclerview in the mainactivity_layout to variable mainRecyclerView
        mainRecyclerView = findViewById(R.id.recyclerview_movielist);

        //mainRecyclerView.setHasFixedSize(true);

        // Load the adapters with a blank dataset.
        // TODO : Replace blank ArrayLists with existing Datasets from Firestore (cache)
        parentAdapter = new MainListAdapter(new ArrayList<>(), this);

        System.out.println(parentAdapter);
        System.out.println(mainRecyclerView);

        // set the parentAdapter to the mainrecyclerview
        mainRecyclerView.setAdapter(parentAdapter);
        DataMigration.getFactory().getListDao().readIntoAdapter(parentAdapter); // Async
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        Intent detailIntent = new Intent(this, DetailActivity.class);
//        DataMigration.getFactory().getMovieDao().readIntoIntent(detailIntent, this, "299537");


        // TODO :: set in the parentAdapter.viewHolder the movieAdapter to the recyclerview of that list_item
    /*movieAdapter = new MovieAdapter(new ArrayList<>());
    DataMigration.getFactory().getMovieDao(550).readIntoAdapter(movieAdapter); // Async*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_search, menu);

        MenuItem searchItem = menu.findItem(R.id.home_search);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                return false;
            }
        });

        return true;

    }


}
