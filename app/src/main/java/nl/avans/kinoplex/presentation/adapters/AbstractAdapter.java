package nl.avans.kinoplex.presentation.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.DomainObject;
import nl.avans.kinoplex.presentation.viewholders.AbstractViewHolder;

public abstract class AbstractAdapter<M extends AbstractViewHolder>
        extends RecyclerView.Adapter<M> {
    private List<DomainObject> dataSet;

    public AbstractAdapter(List<DomainObject> dataSet) {
        Constants.adapterHashes.put(String.valueOf(dataSet.hashCode()), this);
        this.dataSet = dataSet;
    }

    // Replace the entire dataset (used for Collection collection)
    public void updateDataSet(List<DomainObject> dataSet) {
        this.dataSet = dataSet;
        Constants.adapterHashes.put(String.valueOf(dataSet.hashCode()), this);
        notifyDataSetChanged();
    }

    // Add to the dataset, but don't replace it (used for Document collection)
    public void addToDataSet(DomainObject domainObject) {
        this.dataSet.add(domainObject);
        Constants.adapterHashes.put(String.valueOf(dataSet.hashCode()), this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    List<DomainObject> getDataSet() {
        return dataSet;
    }
}
