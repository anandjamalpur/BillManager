package com.afbb.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.afbb.adapter.ParentListAdapter;
import com.afbb.bean.ParentListBean;
import com.afbb.billmanager.AddBillActivity;
import com.afbb.billmanager.R;
import com.afbb.billmanager.RecurringActivity;
import com.afbb.database.BillDatabase;
import com.afbb.utill.Utility;

/**
 * This fragment provides the basic functionality to add a bill
 */
public class ListBillFragment extends Fragment {

	private Button previousButton, nextButton, addButton, sortButton;
	private TextView recurringTextView, monthYearTV, overDueTv, upcomingTv, paidTv;
	private BillDatabase database;
	private int day, month, year;
	private ListView parentListView;
	private ArrayList<ParentListBean> parentListBeans;
	private ParentListAdapter parentAdapter;
	private static final String tag = "ListBillFragment";
	private PopupWindow popupWindow;
	int listCategory = 0;
	private ProgressDialog progressDialog;

	/**
	 * Flag to show total data
	 */
	public static final int ALL = 0;

	/**
	 * Flag to show over due data only
	 */
	public static final int OVER_DUE = 1;
	/**
	 * Flag to show upcoming data only
	 */
	public static final int UPCOMING = 2;
	/**
	 * Flag to show paid data only
	 */
	public static final int PAID = 3;

	/**
	 * Initialize the database object
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		database = new BillDatabase(activity);

		// Change the orientation mode to unspecified
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	/**
	 * Create the view for the fragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_list_bill, container, false);

		// Initialize the views
		init(view);

		return view;
	}

	/**
	 * Initialize the views here
	 */
	private void init(View view) {
		previousButton = (Button) view.findViewById(R.id.previousButton);
		nextButton = (Button) view.findViewById(R.id.nextButton);
		addButton = (Button) view.findViewById(R.id.add_button);
		recurringTextView = (TextView) view.findViewById(R.id.recurring_textview);
		monthYearTV = (TextView) view.findViewById(R.id.month_year_tv);
		parentListView = (ListView) view.findViewById(R.id.parentBillsListView);
		sortButton = (Button) view.findViewById(R.id.sortButton);

		Calendar calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DATE);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);

		monthYearTV.setText(Utility.getMonthName(month) + ", " + year);
	}

	@Override
	public void onResume() {
		super.onResume();

		// Register the listeners
		registerListeners();
		LoadData loadData = new LoadData();
		loadData.execute("");
		// parentListBeans = database.getParentListData(day, month, year);
		// parentAdapter = new ParentListAdapter(getActivity(), parentListBeans, day, month, year, ALL);
		// parentListView.setAdapter(parentAdapter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//Cancel the progress bar
		if(progressDialog!=null){
			progressDialog.cancel();
		}
	}

	/**
	 * Load the data in background
	 */
	private class LoadData extends AsyncTask<String, String, String> {

		private Handler handler;

		/**
		 * Show the progress dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Loading");
			progressDialog.show();
			handler = new Handler();
		}

		@Override
		protected String doInBackground(String... params) {
			parentListBeans = database.getParentListData(day, month, year);

			handler.post(runnable);
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(String result) {

			progressDialog.dismiss();
			super.onPostExecute(result);
		}
	}

	
	//Run loading data to the list view in the background
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			parentAdapter = new ParentListAdapter(getActivity(), parentListBeans, day, month, year, listCategory);
			parentListView.setAdapter(parentAdapter);
		}
	};

	/**
	 * Register the listeners for the views here
	 */
	private void registerListeners() {

		ClickListener listener = new ClickListener();
		previousButton.setOnClickListener(listener);
		nextButton.setOnClickListener(listener);
		addButton.setOnClickListener(listener);
		recurringTextView.setOnClickListener(listener);
		sortButton.setOnClickListener(listener);

	}

	/**
	 * This is the listener class for the views in the fragment
	 */
	public class ClickListener implements OnClickListener {

		/**
		 * Call back method
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			// If previous button is clicked
			case R.id.previousButton:

				month--;
				updateList();

				break;
			// If next button is clicked
			case R.id.nextButton:
				month++;
				updateList();

				break;

			// If add button is clicked
			case R.id.add_button:
				// Show the add bill activity
				Utility.startNextActivity(getActivity(), AddBillActivity.class);
				break;
			// If recurring text view is clicked
			case R.id.recurring_textview:
				// Show the recurring activity
				Utility.startNextActivity(getActivity(), RecurringActivity.class);
				break;

			// If sort button clicked then show the popup
			case R.id.sortButton:
				popupWindow = new PopupWindow(getActivity().getApplicationContext());
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View popupLayout = inflater.inflate(R.layout.sort_menu, null);
				popupWindow.setContentView(popupLayout);

				overDueTv = (TextView) popupLayout.findViewById(R.id.overDueTextView);
				upcomingTv = (TextView) popupLayout.findViewById(R.id.upcomingTextView);
				paidTv = (TextView) popupLayout.findViewById(R.id.paidTextView);

				overDueTv.setOnClickListener(this);
				upcomingTv.setOnClickListener(this);
				paidTv.setOnClickListener(this);

				popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
				popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

				// Close the pop up window when touched out of it
				popupWindow.setOutsideTouchable(true);
				popupWindow.setFocusable(true);

				popupWindow.showAsDropDown(view, 0, 0);

				break;

			// If over due text view clicked
			case R.id.overDueTextView:
				listCategory = OVER_DUE;
				upDateList();
				popupWindow.dismiss();
				break;

			// If over Upcoming text view clicked
			case R.id.upcomingTextView:
				listCategory = UPCOMING;
				upDateList();
				popupWindow.dismiss();
				break;
			// If over paid text view clicked
			case R.id.paidTextView:
				listCategory = PAID;
				upDateList();
				popupWindow.dismiss();

			}
		}

		private void updateList() {

			// If moth count exceeded the max of the year then
			if (month > 11) {
				month = 0;
				year++;
			}

			// If moth count exceeded the min of the year then
			else if (month < 0) {
				month = 11;
				year--;
			}

			/* Now if the month is current month then date is same as real date but if the month is the future month then day is 1 */
			Calendar calendar = Calendar.getInstance();
			int currentMonth = calendar.get(Calendar.MONTH);
			int currentYear = calendar.get(Calendar.YEAR);
			if ((currentYear < year) || (currentMonth != 0 && currentMonth < month) || (currentMonth == 11 && month == 0)) {
				day = 1;
			}

			else if ((currentYear > year) || currentMonth != 0 && currentMonth > month || (currentMonth == 0 && month == 11)) {
				day = 31;
			}

			else if (currentMonth == month && currentMonth == year) {
				day = calendar.get(Calendar.DATE);
			}

			// Set the current month and year to the month text view
			monthYearTV.setText(Utility.getMonthName(month) + ", " + year);

			// Update the list view
			upDateList();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(tag, requestCode + "  " + resultCode);
		upDateList();
	}

	/**
	 * This will update the list view
	 */
	public void upDateList() {
		LoadData data = new LoadData();
		data.execute("");
//		parentListBeans = database.getParentListData(day, month, year);
//		parentAdapter = new ParentListAdapter(getActivity(), parentListBeans, day, month, year, listCategory);
//		parentListView.setAdapter(parentAdapter);
	}
}
