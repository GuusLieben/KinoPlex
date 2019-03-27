package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.FirestoreDaoFactory;
import nl.avans.kinoplex.presentation.adapters.ListAdapter;

public class MainActivity extends AppCompatActivity {

  RecyclerView.Adapter listAdapter;
  RecyclerView.Adapter movieAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listAdapter = new ListAdapter(new ArrayList<>());
    new FirestoreDaoFactory().getListDao().readIntoAdapter(listAdapter);

    movieAdapter = new ListAdapter(new ArrayList<>());
    new FirestoreDaoFactory().getMovieDao(550).readIntoAdapter(movieAdapter);
  }
}
