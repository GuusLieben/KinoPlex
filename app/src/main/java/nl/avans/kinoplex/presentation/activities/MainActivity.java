package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;

import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;
import nl.avans.kinoplex.presentation.adapters.MainListAdapter;

public class MainActivity extends AppCompatActivity{

  private MainListAdapter parentAdapter;
  MainMovieAdapter movieAdapter;
  RecyclerView mainRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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

    // TODO :: set in the parentAdapter.viewHolder the movieAdapter to the recyclerview of that list_item
    movieAdapter = new MainMovieAdapter(new ArrayList<>());
    DataMigration.getFactory().getMovieDao(550).readIntoAdapter(movieAdapter); // Async
  }
}


