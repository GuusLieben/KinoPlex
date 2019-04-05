package nl.avans.kinoplex;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.domain.Constants;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PopulationUnitTest {

    private FirebaseFirestore db;

    @Before
    public void getInstance() {
        db = FirestoreUtils.getInstance();
    }

    /*
     * To prevent unexpected costs for database bandwidth and storage,
     * please keep the iteration count below 100 when testing. Otherwise
     * it costs 0.18 cents (EU) per execution after the bandwidth limit
     * is exceeded.
     * */

    @Test
    public void populateDatabase() {
        int iterations = 5;

        for (int iteration = 0; iteration < iterations; iteration++) {
            String username = "user" + FirestoreUserDao.md5(String.valueOf(iteration));
            String password = FirestoreUserDao.md5("unitPassword" + String.valueOf(iteration));
            String fullname = "Unit User " + iteration;

            final Map<String, String> userData = new HashMap<String, String>() {{
                put("fullname", fullname);
                put("password", password);
            }};

            db.collection(Constants.COL_USERS)
                    .document(username)
                    .set(userData).addOnSuccessListener(aVoid -> {
                Log.d("UnitTest", "Successfully wrote document");
                assertTrue(true);
            }).addOnFailureListener(aVoid -> fail());
        }
    }
}
