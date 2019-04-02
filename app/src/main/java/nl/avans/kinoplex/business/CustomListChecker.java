package nl.avans.kinoplex.business;

import android.util.Log;

public class CustomListChecker {

    public static boolean isCustomList(String listname) {
        Log.d(CustomListChecker.class.getCanonicalName(), "Checking if list " + listname + " is a custom list");

        if (listname.equalsIgnoreCase("Now_playing")) {
            return false;
        } else if (listname.equalsIgnoreCase("Popular")) {
            return false;
        } else if (listname.equalsIgnoreCase("Top_rated")) {
            return false;
        } else if (listname.equalsIgnoreCase("Upcoming")) {
            return false;
        } else {
            return true;
        }
    }
}
