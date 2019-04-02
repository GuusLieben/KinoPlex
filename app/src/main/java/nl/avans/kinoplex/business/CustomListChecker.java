package nl.avans.kinoplex.business;

import android.util.Log;

import nl.avans.kinoplex.R;

public class CustomListChecker {

    public static boolean isCustomList(String listname) {
        Log.d(CustomListChecker.class.getCanonicalName(), "Checking if list " + listname + " is a custom list");

        if (listname.equals("Now_playing")) {
            return false;
        } else if (listname.equals("Popular")) {
            return false;
        } else if (listname.equals("Top_rated")) {
            return false;
        } else {
            return true;
        }
    }
}
