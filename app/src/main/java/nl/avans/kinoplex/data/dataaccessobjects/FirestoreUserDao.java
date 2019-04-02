package nl.avans.kinoplex.data.dataaccessobjects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;

import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.presentation.activities.LoginActivity;

public class FirestoreUserDao implements DaoObject<Pair> {

    private FirebaseFirestore db;

    public FirestoreUserDao() {
        this.db = FirestoreUtils.getInstance();
    }

    @Override
    public boolean create(Pair pair) {
        if (pair == null) {
            return false;
        }

        if (pair.first == null || pair.second == null) {
            return false;
        }

        if (pair.first.equals("") || pair.second.equals("")) {
            return false;
        }

        HashMap<String, Object> userData =
                new HashMap<String, Object>() {
                    {
                        put("fullname", pair.first);
                        String password = md5(String.valueOf(pair.second));
                        put("password", password);
                    }
                };
        Constants.editor.putString("userId", pair.first.toString().replace(" ", "").toLowerCase());
        Constants.editor.apply();
        db.collection(Constants.COL_USERS)
                .document(pair.first.toString().replace(" ", "").toLowerCase())
                .set(userData);
        return true;
    }

    @Override
    public void readIntoAdapter(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readIntoIntent(Intent intent, Context context, Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readAll(RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException();
    }

    public void startIntentIfLoginValid(
            Pair<String, String> credentials, Context context, Intent intent, LoginActivity activity) {
        activity.showLoadingScreen();

        if (credentials == null) {
            activity.showLoginError();
            return;
        }

        if (context == null) {
            activity.showLoginError();
            return;
        }

        if (intent == null) {
            activity.showLoginError();
            return;
        }

        final String username = credentials.first;
        final String password = md5(String.valueOf(credentials.second));

        if (username == null || password == null) {
            activity.showLoginError();
            return;
        }

        if (username.equals("") || password.equals("")) {
            activity.showLoginError();
            return;
        }

        FirebaseFirestore db = FirestoreUtils.getInstance();
        db.collection(Constants.COL_USERS)
                .document(username)
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            final String hashedDocPass = documentSnapshot.getString("password");
                            Constants.editor.putString("userId", username.replace(" ", "").toLowerCase());
                            Constants.editor.apply();

                            if (password.equalsIgnoreCase(hashedDocPass)) {
                                context.startActivity(intent);
                                activity.finish();
                            } else {
                                activity.showLoginError();
                            }
                        });
    }

    public void startIntentOnSavedCredentials(
            Pair<String, String> credentials, Context context, Intent intent, Activity activity) {
        Log.d(Constants.LOGINMANGER_TAG, "Attempting to log the user in on saved credentials...");
        String username = credentials.first;
        String password = credentials.second;

        FirebaseFirestore db = FirestoreUtils.getInstance();
        db.collection(Constants.COL_USERS)
                .document(username)
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            final String hashedDocPass = documentSnapshot.getString("password");
                            if (password.equalsIgnoreCase(hashedDocPass)) {
                                Log.d(Constants.LOGINMANGER_TAG, "Logging the user in...");
                                context.startActivity(intent);
                                Constants.editor.putString("userId", documentSnapshot.getId());
                                Constants.editor.apply();
                                activity.finish();
                            } else {
                                Intent loginIntent = new Intent(context, LoginActivity.class);
                                context.startActivity(loginIntent);
                            }
                        });
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean update(Pair pair) {

        String userId = Constants.pref.getString("userId", "-1");
        return true;
    }

    @Override
    public boolean delete(Pair pair) {
        throw new UnsupportedOperationException();
    }
}
