package nl.avans.kinoplex.data.dataaccessobjects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.domain.Constants;

public class FirestoreUserDao implements DaoObject<Pair> {

    private FirebaseFirestore db;

    public FirestoreUserDao() {
        this.db = FirestoreUtils.getInstance();
    }

    @Override
    public boolean create(Pair pair) {
        String id = db.collection(Constants.COL_USERS).document().getId();
        // TODO : Add a line to store UserID to shared preferences
        HashMap<String, Object> userData =
                new HashMap<String, Object>() {
                    {
                        put("fullname", pair.first);
                        put("username", pair.first.toString().replace(" ", "").toLowerCase());
                        String password = md5(String.valueOf(pair.second));
                        put("password", password);
                    }
                };
        db.collection(Constants.COL_USERS).document(id).set(userData);
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

    public void startIntentIfLoginValid(Pair<String, String> credentials, Context context, Intent intent) {
        String username = credentials.first;
        String password = md5(String.valueOf(credentials.second));

        FirebaseFirestore db = FirestoreUtils.getInstance();
        db.collection(Constants.COL_USERS).document(username).get().addOnSuccessListener(documentSnapshot -> {
            String hashedDocPass = documentSnapshot.getString("password");
            if (password.equals(hashedDocPass)) {
                context.startActivity(intent);
            }
        });
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
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
        // ToDO : Add a line to collect UserID from shared preferences
        return true;
    }

    @Override
    public boolean delete(Pair pair) {
        throw new UnsupportedOperationException();
    }
}
