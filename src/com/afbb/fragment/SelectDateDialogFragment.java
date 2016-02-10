package com.afbb.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afbb.billmanager.R;
import com.afbb.billmanager.ReportsActivity;
import com.afbb.utill.Utility;

/**
 * This is the dialog fragment to get the start date and date from the user
 */
public class SelectDateDialogFragment extends DialogFragment {

	private Dialog inputDialog;
	private EditText startDateEditText, endDateEditText;
	private Button submitBtn, cancelBtn;
	private ImageView dateImageView1, dateImageView2;

	/**
	 * set the view to the dialog fragment
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		inputDialog = new Dialog(getActivity());

		inputDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		inputDialog.setContentView(R.layout.fragment_report_date_selection);
		inputDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		init();

		return inputDialog;
	}

	/**
	 * Initialize the views
	 */
	private void init() {
		startDateEditText = (EditText) inputDialog.findViewById(R.id.start_dateEditText);
		endDateEditText = (EditText) inputDialog.findViewById(R.id.end_dateEditText);
		submitBtn = (Button) inputDialog.findViewById(R.id.submitButton);
		cancelBtn = (Button) inputDialog.findViewById(R.id.cancelButton);
		dateImageView1 = (ImageView) inputDialog.findViewById(R.id.dateImageView1);
		dateImageView2 = (ImageView) inputDialog.findViewById(R.id.dateImageView2);

	}

	/**
	 * Business logic
	 */
	@Override
	public void onResume() {
		super.onResume();
		// Register the listeners
		registerListeners();
	}

	/**
	 * Register the listeners
	 */
	private void registerListeners() {

		submitBtn.setOnClickListener(new ButtonListener());
		cancelBtn.setOnClickListener(new ButtonListener());
		dateImageView1.setOnClickListener(new ButtonListener());
		dateImageView2.setOnClickListener(new ButtonListener());

	}

	/**
	 * This is the listener class for the view registered with this listener in the fragment
	 */
	private class ButtonListener implements OnClickListener {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.US);

		/**
		 * Call back method
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.submitButton:

				try {
					String startDateStr = startDateEditText.getText().toString();
					String endDateStr = endDateEditText.getText().toString();

					Date startDate = dateFormat.parse(startDateStr);
					Date endDate = dateFormat.parse(endDateStr);

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					long startMillis = calendar.getTimeInMillis();

					calendar.setTime(endDate);
					long endMillis = calendar.getTimeInMillis();

					// Validate the date
					if (startMillis > endMillis) {
						Toast.makeText(getActivity(), "End date should after the start date", Toast.LENGTH_SHORT).show();
						return;
					}

					((ReportsActivity) getActivity()).setFromMillis(startMillis);
					((ReportsActivity) getActivity()).setToMillis(endMillis);
					((ReportsActivity) getActivity()).refresh();
					dismiss();

				} catch (Exception e) {
				}

				break;

			// If cancel button clicked then cancel the dialog
			case R.id.cancelButton:
				dismiss();

				break;

			// If date image view1 selected
			case R.id.dateImageView1:

				BillDatePicker datePicker = new BillDatePicker();
				datePicker.show(getFragmentManager(), "bill3");

				break;

			// If date image view2 is selected
			case R.id.dateImageView2:
				BillDatePicker datePicker2 = new BillDatePicker();
				datePicker2.show(getFragmentManager(), "bill4");
				break;

			}
		}

	}

	public void setDate(int day, int month, int year, int id) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.UK);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String dateString = dateFormat.format(calendar.getTime());
		if (id == 3) {
			startDateEditText.setText(dateString);
		}

		else {
			endDateEditText.setText(dateString);
		}
	}

}
