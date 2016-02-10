package com.afbb.billmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.afbb.fragment.BarChartFragment;
import com.afbb.fragment.FragmentComunicator;
import com.afbb.fragment.PieChartFragment;
import com.afbb.fragment.SelectDateDialogFragment;
import com.afbb.utill.Utility;

/**
 * This is the activity which shows the reports for the bills
 */
public class ReportsActivity extends FragmentActivity {

	public static final String tag = "ReportsActivity";

	// Declare the variables
	private FragmentManager manager;
	private ImageView pieChartImageView, barChartImageView, dateSelectionImageView;
	private TextView dateRangeTv;
	private TextView allTextView, paidTextView, unpaidTextView, pieBottom, barBottom;
	private long fromMillis, toMillis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reports);

		init();

		manager = getSupportFragmentManager();

		// If the activity is created first time
		if (savedInstanceState == null) {
			PieChartFragment pieChartFragment = new PieChartFragment();
			Bundle bundle = new Bundle();
			pieChartFragment.setArguments(bundle);
			manager.beginTransaction().add(R.id.fragmentContainer, pieChartFragment, "fragment").commit();

			// Change the visibility of the button bottoms
			pieBottom.setVisibility(View.VISIBLE);
			barBottom.setVisibility(View.GONE);

			Calendar calendar = Calendar.getInstance();
			String monthRange = calendar.getActualMinimum(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR) + " - "
					+ calendar.getActualMaximum(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
			dateRangeTv.setText(monthRange);
		}
	}

	/**
	 * Save the state of the activity
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("pie", pieBottom.getVisibility());
		outState.putInt("bar", barBottom.getVisibility());

		outState.putLong("from", fromMillis);
		outState.putLong("to", toMillis);

		outState.putString("date_range", dateRangeTv.getText().toString());
	}

	/**
	 * Restore the state
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		pieBottom.setVisibility(savedInstanceState.getInt("pie"));
		barBottom.setVisibility(savedInstanceState.getInt("bar"));

		fromMillis = savedInstanceState.getLong("from");
		toMillis = savedInstanceState.getLong("to");

		dateRangeTv.setText(savedInstanceState.getString("date_range"));
	}

	/**
	 * Initialize views and variables
	 */
	private void init() {
		// Initialize the views
		pieChartImageView = (ImageView) findViewById(R.id.pieChartImageView);
		barChartImageView = (ImageView) findViewById(R.id.barChartImageView);
		dateRangeTv = (TextView) findViewById(R.id.date_range_textView);
		allTextView = (TextView) findViewById(R.id.all_textView);
		paidTextView = (TextView) findViewById(R.id.paid_textView);
		unpaidTextView = (TextView) findViewById(R.id.unpaid_textview);
		dateSelectionImageView = (ImageView) findViewById(R.id.select_date_image_view);

		pieBottom = (TextView) findViewById(R.id.pieBottom);
		barBottom = (TextView) findViewById(R.id.barBottom);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		fromMillis = calendar.getTimeInMillis();
		setFromMillis(fromMillis);

		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.add(Calendar.DATE, 1);

		toMillis = calendar.getTimeInMillis();
		setToMillis(toMillis);
	}

	/**
	 * Register the listeners for the views
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Register the listeners here
		registerTheListeners();

	}

	/**
	 * Register the listeners
	 */
	public void registerTheListeners() {
		ReportsClickListener clickListener = new ReportsClickListener();
		pieChartImageView.setOnClickListener(clickListener);
		barChartImageView.setOnClickListener(clickListener);
		allTextView.setOnClickListener(clickListener);
		paidTextView.setOnClickListener(clickListener);
		unpaidTextView.setOnClickListener(clickListener);
		dateSelectionImageView.setOnClickListener(clickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reports, menu);
		return true;
	}

	/**
	 * This is the listener class for the registered views in the activity with this class
	 */
	public class ReportsClickListener implements OnClickListener {

		/**
		 * This method is called when the views registered with this class are called
		 */
		@Override
		public void onClick(View view) {

			Fragment visibleFragment = manager.findFragmentByTag("fragment");

			switch (view.getId()) {
			// If clicked on the pie chart image view
			case R.id.pieChartImageView:

				Log.d(tag, "Clicked on pie chart");

				// If the current visible fragment is not the PieChartFragment then replace the BarChartFragment
				if (!(visibleFragment instanceof PieChartFragment)) {
					PieChartFragment pieChartFragment = new PieChartFragment();
					manager.beginTransaction().replace(R.id.fragmentContainer, pieChartFragment, "fragment").commit();
					pieBottom.setVisibility(View.VISIBLE);
					barBottom.setVisibility(View.GONE);
				}

				break;
			// If clicked on the bar chart image view
			case R.id.barChartImageView:

				Log.d(tag, "Clicked on bar chart");

				// If the current visible fragment is not the BarFragment then replace the with PieChartFragment
				if (!(visibleFragment instanceof BarChartFragment)) {
					BarChartFragment barChartFragment = new BarChartFragment();
					manager.beginTransaction().replace(R.id.fragmentContainer, barChartFragment, "fragment").commit();
					pieBottom.setVisibility(View.GONE);
					barBottom.setVisibility(View.VISIBLE);
				}
				break;

			// If All button is clicked
			case R.id.all_textView:
				// If visible fragment of type FragmentComunicator
				if (visibleFragment instanceof FragmentComunicator) {
					((FragmentComunicator) visibleFragment).showAllData();
				}

				break;

			// If paid text view is clicked
			case R.id.paid_textView:

				// If visible fragment of type FragmentComunicator
				if (visibleFragment instanceof FragmentComunicator) {
					((FragmentComunicator) visibleFragment).showPaidData();
				}

				break;
			// If unpaid text view is clicked
			case R.id.unpaid_textview:
				// If visible fragment of type FragmentComunicator
				if (visibleFragment instanceof FragmentComunicator) {
					((FragmentComunicator) visibleFragment).showunPaidData();
				}
				break;
			// Select date image view is clicked then show the dialog to take input
			case R.id.select_date_image_view:
				SelectDateDialogFragment dialogFragment = new SelectDateDialogFragment();
				dialogFragment.show(getSupportFragmentManager(), "frag");
				break;

			}
		}

	}

	/**
	 * @return the fromMillis
	 */
	public long getFromMillis() {
		return fromMillis;
	}

	/**
	 * @return the toMillis
	 */
	public long getToMillis() {
		return toMillis;
	}

	/**
	 * @param fromMillis
	 *            the fromMillis to set
	 */
	public void setFromMillis(long fromMillis) {
		this.fromMillis = fromMillis;
	}

	/**
	 * @param toMillis
	 *            the toMillis to set
	 */
	public void setToMillis(long toMillis) {
		this.toMillis = toMillis;
	}

	/**
	 * This will refresh the current fragment
	 */
	public void refresh() {

		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.US);

		// Set the date range text view in title screen
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(fromMillis);
		String dateRange = "";
		dateRange = dateFormat.format(calendar.getTime());

		calendar.setTimeInMillis(toMillis - 1);
		calendar.add(Calendar.DATE, 1);
		dateRange = dateRange + " - " + dateFormat.format(calendar.getTime());

		dateRangeTv.setText(dateRange);

		// Now update the fragment with new data
		Fragment visibleFragment = manager.findFragmentByTag("fragment");
		if (visibleFragment instanceof FragmentComunicator) {
			((FragmentComunicator) visibleFragment).reload(getFromMillis(), getToMillis());
		}
	}

}
