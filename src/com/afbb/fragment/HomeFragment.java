package com.afbb.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.afbb.adapter.CalendarAdapter;
import com.afbb.adapter.HomeListAdapter;
import com.afbb.bean.CalendarDayBean;
import com.afbb.bean.HomeListBean;
import com.afbb.billmanager.AddBillActivity;
import com.afbb.billmanager.DetailedViewActivity;
import com.afbb.billmanager.R;
import com.afbb.billmanager.RecurringActivity;
import com.afbb.database.BillDatabase;
import com.afbb.utill.Utility;

/**
 * This is the home fragment for the application
 */
public class HomeFragment extends Fragment {

	public static final String tag = "HomeFragment";
	/**
	 * Declaring variables
	 */
	private GridView calenderGrid;
	private CalendarAdapter calendarAdapter;
	private ArrayList<CalendarDayBean> daysList;
	private TextView monthTextView, dateTextView;
	private Button prevBtn, nextBtn, addButton;
	private int currentDay;
	private int currentMonth;
	private int currentYear;
	private BillDatabase billDatabase;
	private ListView homeList;
	private ArrayList<HomeListBean> homeArrayList;
	private HomeListAdapter homeListAdapter;
	private TextView recurringTv, payableSumTv, receivableSumTv;

	/**
	 * Set the orientation and instantiate the database object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		billDatabase = new BillDatabase(getActivity());

		// Fix the orientation
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * Create the layout and initialize the views in the layout
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View homeFragView = inflater.inflate(R.layout.fragment_home, container, false);

		// Initialize the views and class level variables
		init(homeFragView, savedInstanceState);

		return homeFragView;
	}

	/**
	 * Initialize the views
	 */
	private void init(View homeFragView, Bundle savedInstanceState) {

		calenderGrid = (GridView) homeFragView.findViewById(R.id.calenderGridView);
		monthTextView = (TextView) homeFragView.findViewById(R.id.monthTextView);
		prevBtn = (Button) homeFragView.findViewById(R.id.previousButton);
		nextBtn = (Button) homeFragView.findViewById(R.id.nextButton);
		dateTextView = (TextView) homeFragView.findViewById(R.id.dateTextView);
		addButton = (Button) homeFragView.findViewById(R.id.add_button);
		homeList = (ListView) homeFragView.findViewById(R.id.todayListView);
		recurringTv = (TextView) homeFragView.findViewById(R.id.recurring_textview);
		payableSumTv = (TextView) homeFragView.findViewById(R.id.payable);
		receivableSumTv = (TextView) homeFragView.findViewById(R.id.receivable);

		// If the fragment is created first time
		if (savedInstanceState == null) {
			Log.d(tag, "Hi");
			Calendar calendar = Calendar.getInstance();
			currentDay = calendar.get(Calendar.DATE);
			currentMonth = calendar.get(Calendar.MONTH);
			currentYear = calendar.get(Calendar.YEAR);
		}

		// If the orientation change occurred
		else {
			Log.d(tag, "Bye");
			currentDay = savedInstanceState.getInt("day");
			currentMonth = savedInstanceState.getInt("month");
			currentYear = savedInstanceState.getInt("year");
		}

	}

	/**
	 * Save the state of the variables
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("day", currentDay);
		outState.putInt("month", currentMonth);
		outState.putInt("year", currentYear);
	}

	@Override
	public void onResume() {
		super.onResume();

		Log.d(tag, "Date = " + currentDay + "/" + currentMonth + "/" + currentYear);

		// Generate the daysList based on current month and date

		daysList = Utility.fillList(currentMonth, currentYear, getActivity());

		// Set the current month and year to the month text view
		monthTextView.setText(Utility.getMonthName(currentMonth) + ", " + currentYear);

		// Set date to the dateTextView
		dateTextView.setText(Utility.getWeekName(currentDay, currentMonth, currentYear) + ", " + currentDay + "/" + (currentMonth + 1) + "/" + currentYear);

		calendarAdapter = new CalendarAdapter(getActivity(), daysList);
		calenderGrid.setAdapter(calendarAdapter);

		homeArrayList = billDatabase.getBillsByDay(currentDay, currentMonth, currentYear);
		homeListAdapter = new HomeListAdapter(getActivity(), homeArrayList);
		homeList.setAdapter(homeListAdapter);

		// Set the payable and receivable sum
		float payableSum = billDatabase.getPayableSum();
		float receivableSum = billDatabase.getReceivableSum();

		payableSumTv.setText("$" + payableSum);
		receivableSumTv.setText("$" + receivableSum);

		// Register the listeners here
		registerTheListeners();

	}

	/**
	 * Register the listeners
	 */
	private void registerTheListeners() {

		// Register the listener for the button
		prevBtn.setOnClickListener(new ButtonListener());
		nextBtn.setOnClickListener(new ButtonListener());
		addButton.setOnClickListener(new ButtonListener());
		recurringTv.setOnClickListener(new ButtonListener());

		// Register the listener for the grid view
		calenderGrid.setOnItemClickListener(new AdapterListener());
		homeList.setOnItemClickListener(new AdapterListener());
		calenderGrid.setSelected(true);
		homeList.setOnItemLongClickListener(new LongClickListener());

	}

	/**
	 * This is the listener class for the buttons in the layout
	 */
	private class ButtonListener implements OnClickListener {

		/**
		 * Call back method called when clicked on the button
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			// If previous button is clicked
			case R.id.previousButton:

				currentMonth--;
				updateGrid();
				break;

			// If next button is clicked
			case R.id.nextButton:

				currentMonth++;
				updateGrid();
				break;

			// If add button clicked
			case R.id.add_button:
				// Show the add bill activity
				Utility.startNextActivity(getActivity(), AddBillActivity.class);
				break;
			case R.id.recurring_textview:
				// Show the recurring activity
				Utility.startNextActivity(getActivity(), RecurringActivity.class);
				break;
			}

		}

	}

	/**
	 * This method will update the grid with new data
	 */
	public void updateGrid() {

		// If month count exceeded the max of the year then
		if (currentMonth > 11) {
			currentMonth = 0;
			currentYear++;
		}

		// If month count exceeded the min of the year then
		else if (currentMonth < 0) {
			currentMonth = 11;
			currentYear--;
		}

		// Set the payable and receivable sum
		float payableSum = billDatabase.getPayableSum();
		float receivableSum = billDatabase.getReceivableSum();

		payableSumTv.setText("$" + payableSum);
		receivableSumTv.setText("$" + receivableSum);

		// Set the current month and year to the month text view
		monthTextView.setText(Utility.getMonthName(currentMonth) + ", " + currentYear);
		ArrayList<CalendarDayBean> tempDaysList = Utility.fillList(currentMonth, currentYear, getActivity());

		daysList.clear();
		daysList.addAll(tempDaysList);
		calendarAdapter.notifyDataSetChanged();
	}

	/**
	 * This method will update the list with new data
	 */
	public void updateList() {

		homeArrayList = billDatabase.getBillsByDay(currentDay, currentMonth, currentYear);
		homeListAdapter = new HomeListAdapter(getActivity(), homeArrayList);
		homeList.setAdapter(homeListAdapter);
	}

	/**
	 * This is the listener for the grid item
	 */
	public class AdapterListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			switch (parent.getId()) {

			// If the clicked item is the calendar grid item
			case R.id.calenderGridView:

				CalendarDayBean day = (CalendarDayBean) parent.getItemAtPosition(position);

				// If there is date at the clicked item
				if (day != null) {
					currentDay = day.getDate();
					currentMonth = day.getMonth();
					currentYear = day.getYear();

					dateTextView.setText(Utility.getWeekName(day.getDate(), day.getMonth(), day.getYear()) + ", " + day.getDate() + "/" + (day.getMonth() + 1)
							+ "/" + day.getYear());
					ArrayList<HomeListBean> todaysBillsList = billDatabase.getBillsByDay(day.getDate(), day.getMonth(), day.getYear());
					homeListAdapter = new HomeListAdapter(getActivity(), todaysBillsList);
					homeList.setAdapter(homeListAdapter);
				}

				break;

			// If the clicked item is the Today list item
			case R.id.todayListView:
				HomeListBean bean = (HomeListBean) parent.getItemAtPosition(position);
				long itemId = bean.getRecordId();
				Bundle detailedBundel = new Bundle();
				detailedBundel.putLong("id", itemId);
				Utility.startNextActivity(getActivity(), DetailedViewActivity.class, detailedBundel);
				break;
			}
		}

	}

	/**
	 * This is the listener class for the long clicks in the home list view
	 */
	public class LongClickListener implements OnItemLongClickListener {

		/**
		 * Show the delete dialog when long click on the list item
		 */
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

			DeleteRecordDialogFragment deleteRecordDialogFragment = new DeleteRecordDialogFragment();


			Bundle deleteBundle = new Bundle();
			deleteBundle.putLong("id", id);
			deleteRecordDialogFragment.setArguments(deleteBundle);
			deleteRecordDialogFragment.setTargetFragment(HomeFragment.this, 10);
			deleteRecordDialogFragment.show(getFragmentManager(), "delete");
			return true;
		}

	}

	/**
	 * Update the list and grid here
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(tag, requestCode + "  " + resultCode);

		updateGrid();
		updateList();

	}

}
