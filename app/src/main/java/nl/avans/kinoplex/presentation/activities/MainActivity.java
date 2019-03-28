package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.presentation.adapters.MovieAdapter;
import nl.avans.kinoplex.presentation.adapters.ParentAdapter;

public class MainActivity extends AppCompatActivity {

  RecyclerView.Adapter parentAdapter;
  RecyclerView.Adapter movieAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Load the adapters with a blank dataset.
    // TODO : Replace blank ArrayLists with existing Datasets from Firestore (cache)
    parentAdapter = new ParentAdapter(new ArrayList<>());
    DataMigration.getFactory().getListDao().readIntoAdapter(parentAdapter); // Async

    movieAdapter = new MovieAdapter(new ArrayList<>());
    DataMigration.getFactory().getMovieDao(550).readIntoAdapter(movieAdapter); // Async
  }
}
