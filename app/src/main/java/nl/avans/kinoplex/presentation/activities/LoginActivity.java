package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import nl.avans.kinoplex.R;
import nl.avans.kinoplex.domain.Constants;


public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView background = findViewById(R.id.iv_login_background);
        Glide.with(this).load(R.drawable.login_background_poster).into(background);

        usernameEditText = findViewById(R.id.et_login_username);
        passwordEditText = findViewById(R.id.et_login_password);


        registerButton = findViewById(R.id.btn_login_register);
        registerButton.setOnClickListener(this);

        loginButton = findViewById(R.id.btn_login_login);
        loginButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_login_login:
                Log.d(Constants.LOGINACT_TAG, "User wants to log in...");

                break;

            case R.id.btn_login_register:
                Log.d(Constants.LOGINACT_TAG, "User wants to register...");

                break;
        }
    }
}
