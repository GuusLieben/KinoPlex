package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import nl.avans.kinoplex.R;

import static java.security.AccessController.getContext;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView background = findViewById(R.id.iv_login_background);
        Glide.with(this).load(R.drawable.login_background).into(background);


    }
}
