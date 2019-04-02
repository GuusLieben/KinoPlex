package nl.avans.kinoplex.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;
import nl.avans.kinoplex.domain.MovieList;
import nl.avans.kinoplex.presentation.adapters.ListManagerAdapter;


    /*
           CREATED BY: Stijn Schep
           USAGE: Builds a dialog box
     */

public class DialogBuilder {


    public enum Input {
        SINGLE_EDITTEXT, PREFILLED_EDITTEXT
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

                default:
                    final EditText default_input = new EditText(activity);
                    default_input.setPadding(10, 0, 10 ,0);
                    return default_input;

        }
    }


    /**
     * @param activity The activity in which the dialog should be shown
     * @param title The title of the dialog box
     * @param type The type of input that should be shown. Uses inner-Enum Input
     */
    public static void simpleInputBuilder(Activity activity, String title, Input type, RecyclerView.Adapter adapter) {

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

    public static void simpleListEditDialog(Activity activity, String title, Input type,
                                            MovieList list, RecyclerView.Adapter adapter) {

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
}
