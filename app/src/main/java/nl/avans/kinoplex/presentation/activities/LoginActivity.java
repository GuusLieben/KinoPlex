package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;


public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button registerButton;
    private Button loginButton;

    private LinearLayout loginScreen;
    private ProgressBar progressBar;

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

        loginScreen = findViewById(R.id.ll_login_screen);
        progressBar = findViewById(R.id.pb_login_loading);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_login_login:
                Log.d(Constants.LOGINACT_TAG, "User wants to log in...");
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Pair<String, String> credentials = new Pair<>(username, password);

                Intent mainIntent = new Intent(this, MainActivity.class);
                ((FirestoreUserDao) DataMigration.getFactory().getUserDao())
                        .startIntentIfLoginValid(credentials, this, mainIntent, this);

                break;

            case R.id.btn_login_register:
                Log.d(Constants.LOGINACT_TAG, "User wants to register...");
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    public void showLoginError() {
        Log.d(Constants.LOGINACT_TAG, "Login failed, showing error...");

        showLoginScreen();

        passwordEditText.setText("");
        passwordEditText.setHint("");
        passwordEditText.setBackground(getResources().getDrawable(R.drawable.login_edittext_errorcolor));

        Toast.makeText(this, getResources().getString(R.string.invalidLogin), Toast.LENGTH_LONG).show();
    }

    public void showLoadingScreen() {
        Log.d(Constants.LOGINACT_TAG, "Now showing progress bar...");

        loginScreen.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showLoginScreen() {
        Log.d(Constants.LOGINACT_TAG, "Now showing login screen...");

        progressBar.setVisibility(View.INVISIBLE);
        loginScreen.setVisibility(View.VISIBLE);
    }
}
