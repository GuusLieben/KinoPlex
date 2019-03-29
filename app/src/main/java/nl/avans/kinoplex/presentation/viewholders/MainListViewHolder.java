package nl.avans.kinoplex.presentation.viewholders;

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
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;

public class MainListViewHolder extends AbstractViewHolder {
    private final TextView listTitle;
    private final Button seeAllBtn;
    private final RecyclerView movieListRecylerview;
    private MainMovieAdapter movieAdapter;

    public MainListViewHolder(@NonNull View itemView) {
        super(itemView);

        listTitle = (TextView) itemView.findViewById(R.id.tv_list_title);
        seeAllBtn = (Button) itemView.findViewById(R.id.see_list_btn);
        List<DomainObject> tempList = new ArrayList<DomainObject>();

        movieAdapter = new MainMovieAdapter(new ArrayList<DomainObject>());
        movieListRecylerview = (RecyclerView) itemView.findViewById(R.id.recyclerview_movie_list);
        movieListRecylerview.setLayoutManager(
                new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void bind(DomainObject movieList) {
        setMovieListInAdapter(((MovieList) movieList).getDomainMovieList());
        listTitle.setText(((MovieList) movieList).getName());

        // set see all btn on click listener to open list activity with the Domainobject movieList as parameter
        /*seeAllBtn.setOnClickListener();*/
    }

    private void setMovieListInAdapter(List<DomainObject> list) {
        movieAdapter.updateDataSet(list);
    }

}
