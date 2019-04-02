package nl.avans.kinoplex.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.LoginManager;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.data.factories.TMDbDaoFactory;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.presentation.adapters.MainListAdapter;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    private MainListAdapter parentAdapter;
    RecyclerView mainRecyclerView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Constants.pref =
                getApplicationContext().getSharedPreferences(Constants.PREF_LOGIN, MODE_PRIVATE);
        Constants.editor = Constants.pref.edit();

        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.home));

        // set the recyclerview in the mainactivity_layout to variable mainRecyclerView
        mainRecyclerView = findViewById(R.id.recyclerview_movielist);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem manageListItem = navigationView.getMenu().findItem(R.id.nav_item_add_list);
        SpannableString s = new SpannableString(manageListItem.getTitle());
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.softTextColor)), 0, s.length(), 0);
        manageListItem.setTitle(s);

        // Load the adapters with a blank dataset.
        // TODO : Replace blank ArrayLists with existing Datasets from Firestore (cache)
        parentAdapter = new MainListAdapter(new ArrayList<>(), this);

        ((TMDbDaoFactory) DataMigration.getTMDbFactory()).getGenreDao().readAll(null);

        System.out.println(parentAdapter);
        System.out.println(mainRecyclerView);

        // set the parentAdapter to the mainrecyclerview
        mainRecyclerView.setAdapter(parentAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_account_logout:
                LoginManager.Logout(this, this);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_add_list:
                Intent intent = new Intent(this, ManageListsActivity.class);
                startActivity(intent);
        }


        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(Constants.MAINACT_TAG, "onResume was called");

        if( ManageListsActivity.datahasChanged ) {
            DataMigration.getFactory().getListDao().readIntoAdapter(parentAdapter); // Async

            ManageListsActivity.datahasChanged = false;
        }
    }
}
