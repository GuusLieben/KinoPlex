package nl.avans.kinoplex.business;

import java.util.Random;

import nl.avans.kinoplex.R;

public class PosterPicker {

    public static int getRandomPosterID() {
        Random random = new Random();
        int number = random.nextInt(7);

        switch (number) {
            case 1:
                return R.drawable.login_poster_1;
            case 2:
                return R.drawable.login_poster_2;
            case 3:
                return R.drawable.login_poster_3;
            case 4:
                return R.drawable.login_poster_4;
            case 5:
                return R.drawable.login_poster_5;
            case 6:
                return R.drawable.login_poster_6;
        }

        return R.drawable.login_poster_1;
    }
}
