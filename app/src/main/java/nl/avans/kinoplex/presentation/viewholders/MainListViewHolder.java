package nl.avans.kinoplex.presentation.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.avans.kinoplex.R;

public class MainListViewHolder extends AbstractViewHolder {
    private final TextView movieTitle;
    private final Button seeAllBtn;
    private final RecyclerView movieListRecylerview;
    public MainListViewHolder(@NonNull View itemView) {
        super(itemView);

        movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
        seeAllBtn = (Button) itemView.findViewById(R.id.see_list_btn);
        movieListRecylerview = (RecyclerView) itemView.findViewById(R.id.recyclerview_movie_list);
    }
}
