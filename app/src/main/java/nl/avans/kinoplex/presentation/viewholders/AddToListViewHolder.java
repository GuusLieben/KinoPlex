package nl.avans.kinoplex.presentation.viewholders;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.MovieList;

public class AddToListViewHolder extends AbstractViewHolder {
    private TextView listTitle;
    private FrameLayout frameLayout;
    public AddToListViewHolder(@NonNull View itemView) {
        super(itemView);
        listTitle = itemView.findViewById(R.id.tv_popup_list_title);
        frameLayout = itemView.findViewById(R.id.frame_available_lists_popup);
    }

    public TextView getListTitle() {
        return listTitle;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }
}
