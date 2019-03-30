package nl.avans.kinoplex.presentation.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.AbstractAdapter;
import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;

public class MainListViewHolder extends AbstractViewHolder {
    private final TextView listTitle;
    private final Button seeAllBtn;
    private RecyclerView movieListRecylerview;
    private Context context;

    public MainListViewHolder(@NonNull View itemView) {
        super(itemView);

        context =  itemView.getContext();

        listTitle = itemView.findViewById(R.id.tv_list_title);
        seeAllBtn = itemView.findViewById(R.id.see_list_btn);

        movieListRecylerview = itemView.findViewById(R.id.recyclerview_movie_list);
    }

    public void bind(DomainObject movieList) {

        String name = ((MovieList) movieList).getName();
        if(name.equals("Now_playing")) {
            listTitle.setText(context.getResources().getString(R.string.now_playing));
        } else if(name.equals("Popular")) {
            listTitle.setText(context.getResources().getString(R.string.Popular));
        } else if(name.equals("Top_rated")) {
            listTitle.setText(context.getResources().getString(R.string.top_rated));
        } else {
            listTitle.setText(name);
        }

        List<Movie> movies = ((MovieList) movieList).getMovieList();
        List<DomainObject> domainMovies = new ArrayList<>(movies);

        AbstractAdapter<MainMovieViewHolder> adapter = new MainMovieAdapter(domainMovies);
        movieListRecylerview.setLayoutManager(
                new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        movieListRecylerview.setAdapter(adapter);

        // set see all btn on click listener to open list activity with the Domainobject movieList as parameter
        /*seeAllBtn.setOnClickListener();*/
    }

}
