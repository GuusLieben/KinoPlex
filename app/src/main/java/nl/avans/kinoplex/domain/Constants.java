package nl.avans.kinoplex.domain;

import nl.avans.kinoplex.business.firestoreutils.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.business.taskloaders.ApiCollectorTaskLoader;
import nl.avans.kinoplex.business.taskloaders.ApiSearchTaskLoader;
import nl.avans.kinoplex.business.taskloaders.MovieDetailCollectorTaskLoader;
import nl.avans.kinoplex.business.taskloaders.ReviewDetailCollectorTaskLoader;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreMovieDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreReviewDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.data.factories.FirestoreDaoFactory;
import nl.avans.kinoplex.data.factories.TMDbDaoFactory;
import nl.avans.kinoplex.presentation.activities.MainActivity;
import nl.avans.kinoplex.presentation.activities.SearchActivity;
import nl.avans.kinoplex.presentation.adapters.MovieAdapter;
import nl.avans.kinoplex.presentation.adapters.MainListAdapter;
import nl.avans.kinoplex.presentation.adapters.SearchAdapter;

public class Constants {

    // Urls
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

    // Collections - Firestore
    public static final String COL_LISTS = "lists";
    public static final String COL_MOVIES = "movies";
    public static final String COL_GENRES = "genres";
    public static final String COL_REVIEWS = "reviews";
    public static final String COL_USERS = "users";

    // Tags
    public static final String MOVIEDETAILCOLLECTOR_TAG = MovieDetailCollectorTaskLoader.class.getCanonicalName();
    public static final String REVIEWDETAILCOLLECTOR_TAG = ReviewDetailCollectorTaskLoader.class.getCanonicalName();
    public static final String APICOLLECTOR_TAG = ApiCollectorTaskLoader.class.getCanonicalName();
    public static final String APISEARCH_TAG = ApiSearchTaskLoader.class.getCanonicalName();

    public static final String FIRESTOREUTILS_TAG = FirestoreUtils.class.getCanonicalName();
    public static final String JSONUTILS_TAG = JsonUtils.class.getCanonicalName();

    public static final String FIRESTORELISTDAO_TAG = FirestoreListDao.class.getCanonicalName();
    public static final String FIRESTOREMOVIEDAO_TAG = FirestoreMovieDao.class.getCanonicalName();
    public static final String FIRESTOREREVIEWDAO_TAG = FirestoreReviewDao.class.getCanonicalName();
    public static final String FIRESTOREUSERDAO_TAG = FirestoreUserDao.class.getCanonicalName();

    public static final String FIRESTOREDAOFACTORY_TAG = FirestoreDaoFactory.class.getCanonicalName();
    public static final String TMDBDAOFACTORY_TAG = TMDbDaoFactory.class.getCanonicalName();

    public static final String APPREVIEW_TAG = AppReview.class.getCanonicalName();
    public static final String MOVIE_TAG = Movie.class.getCanonicalName();
    public static final String MOVIELIST_TAG = MovieList.class.getCanonicalName();
    public static final String TMDBREVIEW_TAG = TMDbReview.class.getCanonicalName();

    //    public static final String ADDREVIEWACT_TAG = AddReviewActivity.class.getCanonicalName();
//    public static final String DETAILACT_TAG = DetailActivity.class.getCanonicalName();
//    public static final String LISTACT_TAG = ListActivity.class.getCanonicalName();
//    public static final String LOGINACT_TAG = LoginActivity.class.getCanonicalName();
    public static final String MAINACT_TAG = MainActivity.class.getCanonicalName();
    //    public static final String REGISTERACT_TAG = RegisterActivity.class.getCanonicalName();
//    public static final String REVIEWSACT_TAG = ReviewsActivity.class.getCanonicalName();
    public static final String SEARCHACT_TAG = SearchActivity.class.getCanonicalName();

    //    public static final String LISTADAPT_TAG = ListActivity.class.getCanonicalName();
    public static final String MOVIEADAPT_TAG = MovieAdapter.class.getCanonicalName();
    public static final String PARENTADAPT_TAG = MainListAdapter.class.getCanonicalName();
    //    public static final String REVIEWADAPT_TAG = ReviewAdapter.class.getCanonicalName();
    public static final String SEARCHADAPT_TAG = SearchAdapter.class.getCanonicalName();

//    public static final String LISTVH_TAG  = ListViewHolder.class.getCanonicalName();
//    public static final String MOVIEVH_TAG= MovieViewHolder.class.getCanonicalName();
//    public static final String PARENTVH_TAG= ParentViewHolder.class.getCanonicalName();
//    public static final String REVIEWVH_TAG = ReviewViewHolder.class.getCanonicalName();
//    public static final String SEARCHVH_TAG = SearchViewHolder.class.getCanonicalName();


}
