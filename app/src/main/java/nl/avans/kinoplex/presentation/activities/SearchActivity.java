package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.TMDbListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.data.factories.TMDbDaoFactory;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.SearchAdapter;

public class SearchActivity extends TaskLoaderActivity {
  private SearchAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.searchactivity_layout);

    RecyclerView recyclerView = findViewById(R.id.recyclerview_movie_filter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    adapter = new SearchAdapter(new ArrayList<>());

    DataMigration.getFactory().getMovieDao(512196).readAll(adapter);




      recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_movie, menu);

    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();

    searchView.setOnQueryTextListener(
        new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String s) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            adapter.getFilter().filter(newText);
            return false;
          }
        });
    return true;
  }
}
