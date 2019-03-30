package nl.avans.kinoplex.data.factories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DataMigration {

    private static DaoFactory factory = new FirestoreDaoFactory();
    private static DaoFactory TMDbFactory = new TMDbDaoFactory();

    public static DaoFactory getFactory() {
        return factory;
    }

    public static DaoFactory getTMDbFactory() {
        return TMDbFactory;
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
