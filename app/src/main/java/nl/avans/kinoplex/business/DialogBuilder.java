package nl.avans.kinoplex.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.activities.ManageListsActivity;
import nl.avans.kinoplex.presentation.adapters.ListManagerAdapter;

/** The type Dialog builder. */
public class DialogBuilder {

  /** The enum Input. */
  public enum Input {
    /** Single edittext input. */
    SINGLE_EDITTEXT,
    /** Prefilled edittext input. */
    PREFILLED_EDITTEXT,
    /** Edittext with label input. */
    EDITTEXT_WITH_LABEL
  }

    private static View getView(Input type, Activity activity, String input) {
        LayoutInflater inflater = activity.getLayoutInflater();
        switch (type) {
            case SINGLE_EDITTEXT:
                return inflater.inflate(R.layout.dialog_single_edittext, null);

            case PREFILLED_EDITTEXT:
                View view = inflater.inflate(R.layout.dialog_single_edittext, null);
                ((EditText) view.findViewById(R.id.dialog_single_input)).setText(input);

                return view;

            case EDITTEXT_WITH_LABEL:
                return inflater.inflate(R.layout.dialog_edittext_with_label, null);

            default:
                    final EditText default_input = new EditText(activity);
                    default_input.setPadding(10, 0, 10 ,0);
                    return default_input;

        }
    }

  /**
   * Simple input builder.
   *
   * @param activity the activity
   * @param title the title
   * @param type the type
   * @param adapter the adapter
   */
  public static void simpleInputBuilder(
      Activity activity, String title, Input type, RecyclerView.Adapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);

        builder.setView(getView(type, activity, null));

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = ((AlertDialog) dialog).findViewById(R.id.dialog_single_input);
                String input = edit.getText().toString();

                String userId = Constants.pref.getString("userId", "-1");
                MovieList newList = new MovieList(input, userId);

                ((FirestoreListDao) DataMigration.getFactory().getListDao())
                        .createListForUser(newList);

                ((ListManagerAdapter) adapter).addToDataSet(newList);

                ManageListsActivity.datahasChanged = true;
                dialog.dismiss();
            }
        });

        String cancel = activity.getResources().getString(R.string.cancel);
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

  /**
   * Simple list edit dialog.
   *
   * @param activity the activity
   * @param title the title
   * @param type the type
   * @param list the list
   * @param adapter the adapter
   */
  public static void simpleListEditDialog(
      Activity activity, String title, Input type, MovieList list, RecyclerView.Adapter adapter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);

        builder.setView(getView(type, activity, list.getName()));


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = ((AlertDialog) dialog).findViewById(R.id.dialog_single_input);
                String input = edit.getText().toString();

                list.setName(input);

                DataMigration.getFactory().getListDao().update(list);

                adapter.notifyDataSetChanged();

                ManageListsActivity.datahasChanged = true;
                dialog.dismiss();
            }
        });

        String cancel = activity.getResources().getString(R.string.cancel);
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

  /**
   * Create filter dialog.
   *
   * @param activity the activity
   * @param filter the filter
   */
  public static void createFilterDialog(AppCompatActivity activity, Filter filter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.filter_title);
        View view = getView(Input.EDITTEXT_WITH_LABEL, activity, null);
        builder.setView(view);
        ((TextView)view.findViewById(R.id.dialog_label_input)).setText(view.getResources().getString(R.string.movieYear));
        EditText editText = view.findViewById(R.id.dialog_input_with_label);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String yearValue = editText.getText().toString();
                filter.filter(yearValue);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
