package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.FirestoreDaoFactory;
import nl.avans.kinoplex.presentation.adapters.ListAdapter;

public class MainActivity extends AppCompatActivity {

  RecyclerView.Adapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    listAdapter = new ListAdapter();
    new FirestoreDaoFactory().getListDao().read(listAdapter);
  }
}
