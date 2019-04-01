package nl.avans.kinoplex.presentation.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.presentation.adapters.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity {

    private ReviewAdapter adapter;
    private RecyclerView reviewRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_recyclelist);

        reviewRecyclerView =  findViewById(R.id.reviewlist_recyclerview);
        adapter = new ReviewAdapter(new ArrayList<>());

        DataMigration.getFactory().getReviewDao(512196).readIntoAdapter(adapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
