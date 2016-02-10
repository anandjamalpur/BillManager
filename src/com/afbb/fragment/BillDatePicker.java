package com.afbb.fragment;

import java.util.Calendar;

import com.afbb.billmanager.AddBillActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

/**
 * This is the date picker dialog for the taking bill date input from the user
 */
public class BillDatePicker extends DialogFragment implements OnDateSetListener {

	private int currentYear, currentMonth, currentDay;
	private AddBillActivityCommunicator communicator;

	/**
	 * Instantiate the communicator object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof AddBillActivity) {
			// If the attached activity is implementing the AddBillActivityCommunicator interface
			if (activity instanceof AddBillActivityCommunicator) {
				communicator = (AddBillActivityCommunicator) activity;
			}

			// Throw exception otherwise
			else {
				throw new ClassCastException(activity.getClass().getName() + " should implement the AddBillActivityCommunicator interface");
			}
		}
	}

	/**
	 * Set current date to the date picker dialog
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Calendar c = Calendar.getInstance();
		currentYear = c.get(Calendar.YEAR);
		currentMonth = c.get(Calendar.MONTH);
		currentDay = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, currentYear, currentMonth, currentDay);
	}

	/**
	 * Capture the date and send to the activity
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

		String tag = getTag();
		if (tag.equals("bill1")) {
			communicator.setDate(dayOfMonth, monthOfYear, year, 1);
		} else if (tag.equals("bill2")) {
			communicator.setDate(dayOfMonth, monthOfYear, year, 2);
		} else if (tag.equals("bill3")) {

			Fragment fragment = getFragmentManager().findFragmentByTag("frag");

			if (fragment instanceof SelectDateDialogFragment) {
				((SelectDateDialogFragment) fragment).setDate(dayOfMonth, monthOfYear, year, 3);
			}

		} else if (tag.equals("bill4")) {
			Fragment fragment = getFragmentManager().findFragmentByTag("frag");

			if (fragment instanceof SelectDateDialogFragment) {
				((SelectDateDialogFragment) fragment).setDate(dayOfMonth, monthOfYear, year, 4);
			}
		}
	}

}
