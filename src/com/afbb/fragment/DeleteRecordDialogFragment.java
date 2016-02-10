package com.afbb.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.afbb.database.BillDatabase;

/**
 * This is the dialog fragment which provides the interface to delete a record from record table
 */
@SuppressLint({ "InlinedApi", "NewApi" })
public class DeleteRecordDialogFragment extends DialogFragment {

	private AlertDialog.Builder builder;

	private BillDatabase billDatabase;

	/**
	 * Instantiate the {@link BillDatabase} object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		billDatabase = new BillDatabase(activity);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

		builder.setTitle("Delete");
		
		builder.setMessage("Do you want to delete?");

		builder.setPositiveButton("OK", new ButtonListener());

		builder.setNegativeButton("Cancel", new ButtonListener());

		return builder.create();
	}

	/**
	 * This is the listener class for the button
	 */
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				long id = getArguments().getLong("id");
				
				billDatabase.deleteRecordByRecordId(id);
				getTargetFragment().onActivityResult(getTargetRequestCode(), 20, null);

				break;

			}
		}

	}
}
