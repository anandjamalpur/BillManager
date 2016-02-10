package com.afbb.billmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afbb.bean.BillBean;
import com.afbb.database.BillDatabase;
import com.afbb.fragment.AddBillActivityCommunicator;
import com.afbb.fragment.BillDatePicker;
import com.afbb.utill.BillException;
import com.afbb.utill.Utility;

/**
 * This is the activity which provides the basic functionality to add a bill
 */
public class AddBillActivity extends FragmentActivity implements AddBillActivityCommunicator {

	public static final String tag = "AddBillActivity";
	// Declaring the variables
	private Spinner categorySpinner;

	private BillDatabase database;

	private ArrayList<String> categoryList;

	private TextView recursionPeriodTv, billEndDateTv, billStartDateTV;

	private Spinner recursionSpinner;

	// private ImageView dateImageView2, dateImageView1;

	private EditText billNameEditText, amountEditText, endDateEditText, startDateEditText, descEditText;

	private RadioButton payableRadiobutton, receivableRadioButton;

	private View recursionPeriodLayout, startDateLayout;

	private Button cancelButton, submitButton;

	private ArrayAdapter<String> adapter;

	private RadioGroup recursionRadioGroup;

	private RadioButton yesRadioButton;

	/**
	 * Initialize the views
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_bill);

		// Initialize the views
		init();

		// Get the data from the database and set the adapter for the spinner
		categoryList = database.getCategoryNamesList();
		categoryList.add("Other");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
		categorySpinner.setAdapter(adapter);

		// If recursion radio button is on then make visible the spinner
		if (yesRadioButton.isChecked()) {
			Log.d(tag, "YES");
			recursionPeriodTv.setVisibility(View.VISIBLE);
			recursionSpinner.setVisibility(View.VISIBLE);

			billStartDateTV.setVisibility(View.VISIBLE);
			startDateEditText.setVisibility(View.VISIBLE);
			// dateImageView1.setVisibility(View.VISIBLE);

			billEndDateTv.setText("End date");
		}

	}

	/**
	 * Initialize the views here
	 */
	private void init() {
		categorySpinner = (Spinner) findViewById(R.id.category_spinner);
		recursionRadioGroup = (RadioGroup) findViewById(R.id.recursionRadioGroup);

		recursionPeriodTv = (TextView) findViewById(R.id.label_recurssion_period);
		billEndDateTv = (TextView) findViewById(R.id.label_end_date);
		billStartDateTV = (TextView) findViewById(R.id.label_start_date);
		recursionSpinner = (Spinner) findViewById(R.id.recurssionSpinner);
		yesRadioButton = (RadioButton) findViewById(R.id.yesRadio);
		// dateImageView1 = (ImageView) findViewById(R.id.dateImageView1);
		// dateImageView2 = (ImageView) findViewById(R.id.dateImageView2);

		billNameEditText = (EditText) findViewById(R.id.billNameEditText);
		amountEditText = (EditText) findViewById(R.id.amountEditText);
		startDateEditText = (EditText) findViewById(R.id.start_dateEditText);
		endDateEditText = (EditText) findViewById(R.id.end_dateEditText);
		descEditText = (EditText) findViewById(R.id.descEditText);

		payableRadiobutton = (RadioButton) findViewById(R.id.payableRadio);
		receivableRadioButton = (RadioButton) findViewById(R.id.receivableRadio);

		cancelButton = (Button) findViewById(R.id.cancelButton);
		submitButton = (Button) findViewById(R.id.submitButton);

		recursionPeriodLayout = findViewById(R.id.recursionPeriodLayout);
		startDateLayout = findViewById(R.id.startDateLayout);

		database = new BillDatabase(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Register the listener for the spinner
		// categorySpinner.setOnItemSelectedListener(new CategorySpinnerListener());
		recursionRadioGroup.setOnCheckedChangeListener(new RadioButtonListener());
		// dateImageView1.setOnClickListener(new BillClickeListener());
		// dateImageView2.setOnClickListener(new BillClickeListener());
		cancelButton.setOnClickListener(new BillClickeListener());
		submitButton.setOnClickListener(new BillClickeListener());
		startDateEditText.setOnClickListener(new BillClickeListener());
		endDateEditText.setOnClickListener(new BillClickeListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_bill, menu);
		return true;
	}

	/**
	 * This will update the spinner in the activity
	 */
	// @Override
	// public void updateSpinner(String categoryName) {
	// ArrayList<String> tempCategoryList = database.getCategoriesList();
	// categoryList.clear();
	// categoryList.addAll(tempCategoryList);
	// categoryList.add("Other");
	// adapter.notifyDataSetChanged();
	// int newCategoryPosition = categoryList.indexOf(categoryName);
	// categorySpinner.setSelection(newCategoryPosition);
	//
	// }

	/**
	 * This is the listener class for the radio buttons in the activity
	 */
	public class RadioButtonListener implements OnCheckedChangeListener {

		/**
		 * Call back method
		 */
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (group.getCheckedRadioButtonId()) {
			case R.id.yesRadio:
				recursionPeriodTv.setVisibility(View.VISIBLE);
				recursionSpinner.setVisibility(View.VISIBLE);
				billStartDateTV.setVisibility(View.VISIBLE);
				startDateEditText.setVisibility(View.VISIBLE);
				// dateImageView1.setVisibility(View.VISIBLE);
				recursionPeriodLayout.setVisibility(View.VISIBLE);
				startDateLayout.setVisibility(View.VISIBLE);
				billEndDateTv.setText("End date");
				break;

			case R.id.noRadio:
				recursionPeriodTv.setVisibility(View.GONE);
				recursionSpinner.setVisibility(View.GONE);
				billStartDateTV.setVisibility(View.GONE);
				startDateEditText.setVisibility(View.GONE);
				// dateImageView1.setVisibility(View.GONE);
				recursionPeriodLayout.setVisibility(View.GONE);
				startDateLayout.setVisibility(View.GONE);
				billEndDateTv.setText("Bill Date");
				break;
			}
		}

	}

	/**
	 * This is the click listener class for the registered views in the activity
	 */
	public class BillClickeListener implements OnClickListener {

		/**
		 * Call back method
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.dateImageView1:
				// BillDatePicker datePicker = new BillDatePicker();
				// datePicker.show(getSupportFragmentManager(), "bill1");
				break;
			// If date image view is clicked
			case R.id.dateImageView2:
				// BillDatePicker datePicker2 = new BillDatePicker();
				// datePicker2.show(getSupportFragmentManager(), "bill2");

				break;

			// If cancel button is clicked
			case R.id.cancelButton:
				finish();
				break;

			// If submit button is clicked
			case R.id.submitButton:
				abbBillToDatabase();
				break;
			case R.id.start_dateEditText:
				BillDatePicker datePicker = new BillDatePicker();
				datePicker.show(getSupportFragmentManager(), "bill1");
				break;
			case R.id.end_dateEditText:
				BillDatePicker datePicker2 = new BillDatePicker();
				datePicker2.show(getSupportFragmentManager(), "bill2");
				break;
			}
		}

	}

	// private int day = -1, month = -1, year = -1;

	/**
	 * This method will add the bill to the database
	 */
	private void abbBillToDatabase() {

		try {

			BillBean billBean = new BillBean();
			String billName = billNameEditText.getText().toString();
			billBean.setName(billName);

			String billCategory = (String) categorySpinner.getSelectedItem();
			billBean.setCategory(billCategory);

			String billType = "";
			// If payable radio button is checked
			if (payableRadiobutton.isChecked()) {
				billType = "payable";
			}
			// If receivable radio button is checked
			else if (receivableRadioButton.isChecked()) {
				billType = "receivable";
			}
			billBean.setType(billType);

			String amount = amountEditText.getText().toString();

			String amountArr[] = amount.split("\\.");

			if (amountArr[0].length() > 6) {
				throw new BillException("Invalid bill amount");
			}

			billBean.setAmount(Float.parseFloat(amount));

			String description = descEditText.getText().toString();
			billBean.setDescription(description);

			boolean isRecursion = false;

			// If recursion is yes
			if (yesRadioButton.isChecked()) {
				isRecursion = true;
			}
			billBean.setRecursive(isRecursion);

			String recursionPeriod = null;
			// If recursion is true
			if (isRecursion) {
				recursionPeriod = (String) recursionSpinner.getSelectedItem();
				billBean.setRecursionPeriod(recursionPeriod);
				billBean.setStartDate(startDateEditText.getText().toString());
				billBean.setEndDate(endDateEditText.getText().toString());
			}

			else {
				billBean.setRecursionPeriod("once");
				billBean.setStartDate(endDateEditText.getText().toString());
				billBean.setEndDate(endDateEditText.getText().toString());
			}

			// Validate the bean
			Utility.validateInputs(billBean);

			database.addBill(billBean);
			finish();
		} catch (NumberFormatException exception) {
			Toast.makeText(this, "Enter the valid bill amount", Toast.LENGTH_SHORT).show();
		} catch (BillException billException) {
			Toast.makeText(this, billException.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception exception) {
			Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * This will set the date to the activity
	 */
	@Override
	public void setDate(int day, int month, int year, int id) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.UK);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String dateString = dateFormat.format(calendar.getTime());

		// String dateString = day + "/" + (month + 1) + "/" + year;

		// If id is 1 then set the date to the start date edit text
		if (id == 1) {
			startDateEditText.setText(dateString);
		}

		// If id is 2 then set the date to the end date edit text
		else {
			endDateEditText.setText(dateString);
		}
		// this.day = day;
		// this.month = month;
		// this.year = year;
	}
}
