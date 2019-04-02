package nl.avans.kinoplex.business;

import android.content.Context;
import android.util.Log;

import nl.avans.kinoplex.R;


public class CustomListChecker {

    public static boolean isCustomList(String listname) {
        Log.d(CustomListChecker.class.getCanonicalName(), "Checking if list " + listname + " is a custom list");

        if (listname.equalsIgnoreCase("!Now_playing")) {
            return false;
        } else if (listname.equalsIgnoreCase("!Popular")) {
            return false;
        } else if (listname.equalsIgnoreCase("!Top_rated")) {
            return false;
        } else if (listname.equalsIgnoreCase("!Upcoming")) {
            return false;
        } else {
            return true;
        }
    }

    public static String returnCorrectTitle(String name, Context context) {
        if (name.equalsIgnoreCase("!Now_playing")) {
            return context.getResources().getString(R.string.now_playing);
        } else if (name.equalsIgnoreCase("!Popular")) {
            return context.getResources().getString(R.string.Popular);
        } else if (name.equalsIgnoreCase("!Top_rated")) {
            return context.getResources().getString(R.string.top_rated);
        } else if (name.equalsIgnoreCase("!Upcoming")) {
            return context.getResources().getString(R.string.upcoming);
        } else {
            return null;
        }
    }
}
