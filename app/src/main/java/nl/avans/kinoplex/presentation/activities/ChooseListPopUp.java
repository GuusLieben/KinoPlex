package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.adapters.AddToListAdapter;

public class ChooseListPopUp extends Activity {
    private TextView movieTitleView;
    private RecyclerView recyclerView;
    private Button addToListButton;
    private ImageView imageViewBg;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        if (getIntent().getExtras() == null ) {
            return;
        }

        String json = getIntent().getStringExtra(Constants.MOVIE_TAG);
        movie = new Gson().fromJson(json, Movie.class);

        movieTitleView = findViewById(R.id.tv_add_movie_to_list);
        recyclerView = findViewById(R.id.recyclerview_available_lists_popup);
        imageViewBg = findViewById(R.id.popup_image_bg);

        Glide.with(movieTitleView)
                .load(movie.getPosterPath())
                .into(imageViewBg);

        movieTitleView.setText("Add '"+ movie.getTitle() + "' to list");
        AbstractAdapter adapter = new AddToListAdapter(getTempList(), movie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<DomainObject> getTempList() {
        List<DomainObject> list = new ArrayList<DomainObject>();
        for ( int i = 0; i < 5; i++ ) {
            list.add(new MovieList("Watched"+i, "user" + i));
        }
        return list;
    }
}
