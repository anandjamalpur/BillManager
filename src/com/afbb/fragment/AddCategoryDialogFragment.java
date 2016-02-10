package com.afbb.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afbb.billmanager.CategoriesActivity;
import com.afbb.billmanager.R;
import com.afbb.database.BillDatabase;
import com.afbb.utill.Utility;

/**
 * This is the dialog which provides the adding category functionality
 */
@SuppressLint("DefaultLocale")
public class AddCategoryDialogFragment extends DialogFragment {

	public static final String tag = "AddCategoryDialogFragment";

	private Dialog dialog;

	private Spinner redSpinner, greenSpinner, blueSpinner;

	private ArrayList<Integer> colorList;

	private TextView colorTextView;

	private Button cancelButton, submitButton;

	private EditText categoryNameEditText;

	private BillDatabase billDatabase;

	private CategoriesCommunicator communicator;

	/**
	 * Instantiate the {@link CategoriesCommunicator} object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// If the attached activity is implementing the CategoriesCommunicator object
		if (activity instanceof CategoriesCommunicator) {
			communicator = (CategoriesCommunicator) activity;
		}

		// Throw exception otherwise
		else {
			throw new ClassCastException(activity.getClass().getName() + " should implement the CategoriesCommunicator interface");
		}
	}

	/**
	 * Instantiate the views
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_category);

		redSpinner = (Spinner) dialog.findViewById(R.id.redSpinner);
		greenSpinner = (Spinner) dialog.findViewById(R.id.greenSpinner);
		blueSpinner = (Spinner) dialog.findViewById(R.id.blueSpinner);
		colorTextView = (TextView) dialog.findViewById(R.id.colorTextView);
		cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
		submitButton = (Button) dialog.findViewById(R.id.submitButton);
		categoryNameEditText = (EditText) dialog.findViewById(R.id.catNameEditText);
		billDatabase = new BillDatabase(getActivity());

		colorList = new ArrayList<Integer>(256);

		for (int i = 0; i < 256; i++) {
			colorList.add(i);
		}

		return dialog;
	}

	/**
	 * This will set the color to the color text view
	 */
	private void setColor(int red, int green, int blue) {
		colorTextView.setBackgroundColor(Color.rgb(red, green, blue));
	}

	@Override
	public void onResume() {
		super.onResume();
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, colorList);
		redSpinner.setAdapter(adapter);
		greenSpinner.setAdapter(adapter);
		blueSpinner.setAdapter(adapter);

		// Register listener for the spinners
		redSpinner.setOnItemSelectedListener(new ColorSpinnreListener());
		greenSpinner.setOnItemSelectedListener(new ColorSpinnreListener());
		blueSpinner.setOnItemSelectedListener(new ColorSpinnreListener());

		// Register listener for the buttons
		cancelButton.setOnClickListener(new ButtonListener());
		submitButton.setOnClickListener(new ButtonListener());
	}

	/**
	 * This is the listener class for the spinner selection
	 */
	public class ColorSpinnreListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

			switch (parent.getId()) {

			// If red spinner is selected
			case R.id.redSpinner:
				setColor(redSpinner.getSelectedItemPosition(), greenSpinner.getSelectedItemPosition(), blueSpinner.getSelectedItemPosition());
				break;
			// If green spinner is selected
			case R.id.greenSpinner:
				setColor(redSpinner.getSelectedItemPosition(), greenSpinner.getSelectedItemPosition(), blueSpinner.getSelectedItemPosition());
				break;
			// If blue spinner is selected
			case R.id.blueSpinner:
				setColor(redSpinner.getSelectedItemPosition(), greenSpinner.getSelectedItemPosition(), blueSpinner.getSelectedItemPosition());
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			Log.d(tag, "onNothingSelected");
		}

	}

	/**
	 * This is the listener class for the buttons in the dialog
	 */
	public class ButtonListener implements OnClickListener {

		/**
		 * Call back method called when the buttons are clicked
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			// If cancel button is clicked then cancel the dialog
			case R.id.cancelButton:
				dialog.dismiss();
				break;

			// If submit button is clicked then add the category to the database
			case R.id.submitButton:
				String categoryName = categoryNameEditText.getText().toString();

				// Check for the validations
				if (categoryName.length() == 0) {
					Toast.makeText(getActivity(), "Category name should not be empty", Toast.LENGTH_SHORT).show();
					return;
				}

				String colorStr = Utility.convertToHexString(redSpinner.getSelectedItemPosition(), greenSpinner.getSelectedItemPosition(),
						blueSpinner.getSelectedItemPosition());

				try {
					billDatabase.addCategory(categoryName, colorStr.toUpperCase());
					communicator.updateList();
					dialog.dismiss();
				} catch (Exception e) {
					Toast.makeText(getActivity(), "The name or color is already exists", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}

	/**
	 * This is the interface which will used to communicate wit {@link CategoriesActivity}
	 */
	public interface CategoriesCommunicator {

		/**
		 * This will update the list view in the {@link CategoriesActivity}
		 */
		public void updateList();
	}

}
