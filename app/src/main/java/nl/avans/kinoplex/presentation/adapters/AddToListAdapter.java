package nl.avans.kinoplex.presentation.adapters;

import android.content.Context;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.domain.Movie;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.viewholders.AddToListViewHolder;

public class AddToListAdapter extends AbstractAdapter<AddToListViewHolder> {
    private Context context;
    private Movie movie;
    public AddToListAdapter(List<DomainObject> dataSet, Movie movie) {
        super(dataSet);
        this.movie = movie;
    }

    @NonNull
    @Override
    public AddToListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        Log.d("AddToListAdapter", "the context -----> " + context.toString());
        int layoutID = R.layout.available_list_popup_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(layoutID, viewGroup, false);

        return new AddToListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToListViewHolder addToListViewHolder, int i) {
        MovieList movieList = (MovieList) getDataSet().get(i);
        addToListViewHolder.getListTitle().setText(movieList.getName());
        addToListViewHolder.getFrameLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddMovieToList", "User wants to add the movie : " + movie.getTitle() + " ; to the list -> " + movieList.getName());
            }
        });
    }


}
