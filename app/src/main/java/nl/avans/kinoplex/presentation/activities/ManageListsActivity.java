package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.business.PosterPicker;

public class ManageListsActivity extends Activity implements View.OnClickListener {
    private Button returnButton;
    private Button addListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lists);

        ImageView background = findViewById(R.id.iv_background_poster);
        Glide.with(this).load(PosterPicker.getRandomPosterID()).into(background);


        returnButton = findViewById(R.id.btn_manage_list_return);
        addListButton = findViewById(R.id.btn_manage_list_add);
        returnButton.setOnClickListener(this);
        addListButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_manage_list_return:
                finish();
                break;

            case R.id.btn_manage_list_add:
                String title = getResources().getString(R.string.enterTitle);
                DialogBuilder.simpleInputBuilder(this, title, DialogBuilder.Input.SINGLE_EDITTEXT);

                break;
        }
    }
}
