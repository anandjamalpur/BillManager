package com.afbb.billmanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.afbb.adapter.RecurringAdapter;
import com.afbb.adapter.RecurringBean;
import com.afbb.database.BillDatabase;
import com.afbb.fragment.DeleteDialogFragment;
import com.afbb.fragment.DeleteDialogFragment.ActivityCommunicator;
import com.afbb.utill.Utility;

/**
 * This activity is to handle recurring bills data
 */
public class RecurringActivity extends FragmentActivity implements ActivityCommunicator {

	public static final String tag = "RecurringActivity";
	// Declare the variables
	private ImageView addButton;
	private ListView recurringListView;
	private RecurringAdapter recurringAdapter;
	private ArrayList<RecurringBean> recurringList;
	private BillDatabase database;

	/**
	 * Initialize the views
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recurring);

		// initialize the views
		init();
	}

	/**
	 * Initialize the vies here
	 */
	private void init() {
		addButton = (ImageView) findViewById(R.id.add_button);
		recurringListView = (ListView) findViewById(R.id.recurringListView);
		database = new BillDatabase(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Set the adapter to the list view
		recurringList = database.getRecurringBills();
		recurringAdapter = new RecurringAdapter(this, recurringList);
		recurringListView.setAdapter(recurringAdapter);

		// Register listener for the add button
		addButton.setOnClickListener(new RecurringButtonListener());
		// Register long click listener for the list view
		recurringListView.setOnItemLongClickListener(new ListLongClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recurring, menu);
		return true;
	}

	/**
	 * This is the listener class for the registered views in the activity
	 */
	private class RecurringButtonListener implements OnClickListener {

		/**
		 * Call back method
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.add_button:
				Utility.startNextActivity(RecurringActivity.this, AddBillActivity.class);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * This is the listener class for the long click events of the list view
	 */
	public class ListLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

			Log.d(tag, id + "");
			DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();

			Bundle bundle = new Bundle();
			bundle.putLong("bill_id", id);
			deleteDialogFragment.setArguments(bundle);

			deleteDialogFragment.show(getSupportFragmentManager(), "delete");
			return true;
		}

	}

	/**
	 * This will update the list view in the activity
	 */
	@Override
	public void updateList() {
		ArrayList<RecurringBean> tempRecurringBeans = database.getRecurringBills();
		recurringList.clear();
		recurringList.addAll(tempRecurringBeans);
		recurringAdapter.notifyDataSetChanged();
	}

}
