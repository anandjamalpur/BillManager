package com.afbb.billmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.afbb.fragment.HomeFragment;
import com.afbb.fragment.ListBillFragment;
import com.afbb.fragment.MoreFragment;
import com.afbb.utill.GlobalData;
import com.afbb.utill.Utility;

/**
 * This is the home activity of the application
 */
public class HomeActivity extends FragmentActivity {

	private static final String tag = "HomeActivity";
	// Declare the variables
	private FragmentManager fragmentManager;
	private TextView calenderView, listView, overviewView, reportsView, moreView;

	/**
	 * Initialize the class level variables
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		// Initialize the views
		calenderView = (TextView) findViewById(R.id.calender);
		listView = (TextView) findViewById(R.id.list);
		overviewView = (TextView) findViewById(R.id.overview);
		reportsView = (TextView) findViewById(R.id.reports);
		moreView = (TextView) findViewById(R.id.more);

		((GlobalData) getApplication()).setColorMap();

		fragmentManager = getSupportFragmentManager();

		// If first time activity created then add the HomeFragment
		if (savedInstanceState == null) {
			HomeFragment fragment = new HomeFragment();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(R.id.fragment_container, fragment, "fragment");
			transaction.commit();
			calenderView.setBackgroundResource(R.drawable.footer_item_shape_pressed);
		}

	}

	/**
	 * Business logic
	 */
	@Override
	protected void onResume() {
		super.onResume();

		Log.d(tag, "onResume called");

		// Register the listeners
		calenderView.setOnClickListener(new ButtonListener());
		listView.setOnClickListener(new ButtonListener());
		overviewView.setOnClickListener(new ButtonListener());
		reportsView.setOnClickListener(new ButtonListener());
		moreView.setOnClickListener(new ButtonListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/**
	 * This will update the grid in the HomeFragment
	 */
	public void updateGrid() {
		Fragment fragment = fragmentManager.findFragmentByTag("fragment");

		// If the current fragment is the HomeFragment
		if (fragment instanceof HomeFragment) {
			((HomeFragment) fragment).updateGrid();
		}
	}

	/**
	 * This is the listener class for the buttons in the {@link HomeActivity}
	 */
	private class ButtonListener implements OnClickListener {

		/**
		 * This method is called when the registered views with this class are clicked
		 */
		@Override
		public void onClick(View view) {

			// Get the current fragment int stack
			Fragment visibleFragment = (Fragment) fragmentManager.findFragmentByTag("fragment");

			switch (view.getId()) {
			case R.id.calender:

				// If the visible fragment is not the Home fragment then only replace the current fragment with Home fragment
				if (!(visibleFragment instanceof HomeFragment)) {
					fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

					calenderView.setBackgroundResource(R.drawable.footer_item_shape_pressed);
					listView.setBackgroundResource(R.drawable.footer_item_shape);
					moreView.setBackgroundResource(R.drawable.footer_item_shape);
				}

				break;

			// If clicked on list
			case R.id.list:

				// If the visible fragment is not the ListBillFragment then only replace the current fragment with ListBillFragment
				if (!(visibleFragment instanceof ListBillFragment)) {
					fragmentManager.beginTransaction().replace(R.id.fragment_container, new ListBillFragment(), "fragment").commit();

					listView.setBackgroundResource(R.drawable.footer_item_shape_pressed);
					calenderView.setBackgroundResource(R.drawable.footer_item_shape);
					moreView.setBackgroundResource(R.drawable.footer_item_shape);
				}
				break;

			// If clicked on reports
			case R.id.reports:
				// Show the reports activity
				Utility.startNextActivity(HomeActivity.this, ReportsActivity.class);
				break;

			case R.id.more:
				// If the visible fragment is not the MoreFragment then only replace the current fragment with MoreFragment
				if (!(visibleFragment instanceof MoreFragment)) {
					fragmentManager.beginTransaction().replace(R.id.fragment_container, new MoreFragment(), "fragment").commit();

					listView.setBackgroundResource(R.drawable.footer_item_shape);
					calenderView.setBackgroundResource(R.drawable.footer_item_shape);
					moreView.setBackgroundResource(R.drawable.footer_item_shape_pressed);
				}
				break;
			}

		}

	}

	/**
	 * This will update the list view in the {@link ListBillFragment}
	 */
	public void updateListView() {
		Fragment fragment = fragmentManager.findFragmentByTag("fragment");

		// If the current fragment is the HomeFragment
		if (fragment instanceof ListBillFragment) {
			((ListBillFragment) fragment).upDateList();
		}
	}

}
