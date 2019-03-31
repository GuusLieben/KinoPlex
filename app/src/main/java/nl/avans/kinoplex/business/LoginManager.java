package nl.avans.kinoplex.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.domain.Constants;

import static android.content.Context.MODE_PRIVATE;

public class LoginManager {

    public static void saveLoginCredentials(Context context, Pair<String, String> credentials) {
        Log.d(Constants.LOGINMANGER_TAG, "Checking context...");

        if(context == null) {
            return;
        }

        if(credentials == null) {
            return;
        }

        if(credentials.first == null || credentials.second == null) {
            return;
        }

        if(credentials.first.equals("") || credentials.second.equals("")) {
            return;
        }

        Log.d(Constants.LOGINMANGER_TAG, "Saving the credentials of the user...");
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.PREF_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(Constants.PREF_USERNAME, credentials.first);
        editor.putString(Constants.PREF_HASHEDPASS, FirestoreUserDao.md5(credentials.second));
        editor.putBoolean(Constants.PREF_AUTOLOGIN, true);

        editor.apply();
    }

    public static Pair<String, String> getLoginCredentials(Context context) {
        if(context == null) {
            return null;
        }

        Log.d(Constants.LOGINMANGER_TAG, "Fetching the credentials of the user...");
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.PREF_LOGIN, MODE_PRIVATE);

        Log.d(Constants.LOGINMANGER_TAG, "Value of AUTOLOGIN: " +
                pref.getBoolean(Constants.PREF_AUTOLOGIN, false));

        if(pref.getBoolean(Constants.PREF_AUTOLOGIN, false)) {
            String username = pref.getString(Constants.PREF_USERNAME, null);
            String hashedPassword = pref.getString(Constants.PREF_HASHEDPASS, null);

            Log.d(Constants.LOGINMANGER_TAG, "Returning the credentials of the user...");
            return new Pair<>(username, hashedPassword);
        }

        Log.d(Constants.LOGINMANGER_TAG, "Could not start user login");
        return null;
    }
}
