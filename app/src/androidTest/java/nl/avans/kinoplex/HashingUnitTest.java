package nl.avans.kinoplex;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HashingUnitTest {

    @Test
    public void MD5HashesCorrectly() {
        String unhashedWord = "kinoplexUnitTest";
        String expectedHash = "336026585745870dbf5663bdd8471808";

        String actualHash = FirestoreUserDao.md5(unhashedWord);
        assertEquals(expectedHash, actualHash);
    }
}
