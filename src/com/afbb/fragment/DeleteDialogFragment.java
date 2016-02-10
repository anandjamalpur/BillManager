package com.afbb.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.afbb.database.BillDatabase;

/**
 * This is the dialog fragment class which provides the interface to delete an item
 */
public class DeleteDialogFragment extends DialogFragment {

	/**
	 * Declare alert dialog
	 */
	private Builder dialogBuilder;

	/**
	 * Specify the deletion type. If zero all or if 1 only unpaid
	 */
	private int deletionType = 0;

	/**
	 * The id of the deleted item
	 */
	private long deleteItemId = -1;

	private BillDatabase billDatabase;

	private ActivityCommunicator communicator;

	/**
	 * Instantiate the communicator object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// If the attached activity is implementing the ActivityCommunicator
		if (activity instanceof ActivityCommunicator) {
			communicator = (ActivityCommunicator) activity;
		}

		// Otherwise throw ClassCastException
		else {
			throw new ClassCastException(activity.toString() + " should implement the ActivityCommunicator interface");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle("Delete").setSingleChoiceItems(new String[] { "All", "Unpaid" }, 0, new Listener())
				.setPositiveButton("Delete", new ButtonListener()).setNegativeButton("Cancel", new ButtonListener());
		billDatabase = new BillDatabase(getActivity());
		deleteItemId = getArguments().getLong("bill_id");


		return dialogBuilder.create();
	}

	/**
	 * While dismissing the dialog update the list view in the activity
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		communicator.updateList();
		super.onDismiss(dialog);
	}

	/**
	 * This is the listener class for the single choice items in the dialog
	 */
	public class Listener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			deletionType = which;
		}

	}

	/**
	 * This is the listener class for the buttons in the dialog
	 */
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			// If delete button is clicked
			case DialogInterface.BUTTON_POSITIVE:
				billDatabase.deletedRecursion(deleteItemId, deletionType);
				break;

			}
		}

	}

	/**
	 * This is the communicator class which is used to communicate between the dialog and attached activity
	 */
	public interface ActivityCommunicator {
		/**
		 * This will update the list view in the activity
		 */
		public void updateList();
	}

}
