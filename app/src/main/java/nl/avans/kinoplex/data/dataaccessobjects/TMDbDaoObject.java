package nl.avans.kinoplex.data.dataaccessobjects;

import android.support.v7.widget.RecyclerView;

import java.util.concurrent.ExecutionException;

import nl.avans.kinoplex.domain.DomainObject;

public interface TMDbDaoObject extends DaoObject {

    DomainObject readCollectionToAdapter(String identifier, int page, RecyclerView.Adapter adapter) throws ExecutionException, InterruptedException;

}
