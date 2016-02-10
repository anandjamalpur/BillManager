package com.afbb.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afbb.adapter.BarChartAdapter;
import com.afbb.bean.Category;
import com.afbb.billmanager.R;
import com.afbb.billmanager.ReportsActivity;
import com.afbb.database.BillDatabase;

/**
 * This is the fragment which contains the pie chart
 */
public class BarChartFragment extends Fragment implements FragmentComunicator {

	private static final String tag = "BarChartFragment";
	private BillDatabase billDatabase;
	private ListView barListView;
	private long fromMillis;
	private long toMillis;
	private ArrayList<Category> categoryList;
	private BarChartAdapter adapter;
	private int presentType;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		billDatabase = new BillDatabase(activity);
		fromMillis = ((ReportsActivity) activity).getFromMillis();
		toMillis = ((ReportsActivity) activity).getToMillis();
	}

	/**
	 * Inflate the layout and initialize the views here
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View barChartLayout = inflater.inflate(R.layout.fragment_barchart, container, false);
		barListView = (ListView) barChartLayout.findViewById(R.id.barListView);
		presentType = TYPE_ALL;

		// Restore the fromMillis and restore toMillis
		if (savedInstanceState != null) {
			fromMillis = savedInstanceState.getLong("from");
			toMillis = savedInstanceState.getLong("to");
			presentType = savedInstanceState.getInt("type");
		}

		return barChartLayout;
	}

	/**
	 * Save the state
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("from", fromMillis);
		outState.putLong("to", toMillis);

		outState.putInt("type", presentType);
	}

	@Override
	public void onResume() {
		super.onResume();

		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View emptyView = inflater.inflate(R.layout.empty_view, null);
		barListView.setEmptyView(emptyView);

		categoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_ALL);
		adapter = new BarChartAdapter(getActivity(), categoryList);
		barListView.setAdapter(adapter);
		reload(fromMillis, toMillis);
	}

	/**
	 * This will show total data in the chart
	 */
	@Override
	public void showAllData() {
		presentType = TYPE_ALL;
		ArrayList<Category> tempCategoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_ALL);
		categoryList.clear();
		categoryList.addAll(tempCategoryList);
		adapter.notifyDataSetChanged();

	}

	/**
	 * This will show paid data in the chart
	 */
	@Override
	public void showPaidData() {
		presentType = TYPE_PAID;
		ArrayList<Category> tempCategoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_PAID);

		Log.d(tag, tempCategoryList.size() + "");
		categoryList.clear();
		categoryList.addAll(tempCategoryList);
		adapter.notifyDataSetChanged();

	}

	/**
	 * This will show unpaid data in the chart
	 */
	@Override
	public void showunPaidData() {
		presentType = TYPE_UNPAID;
		ArrayList<Category> tempCategoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_UNPAID);
		categoryList.clear();
		categoryList.addAll(tempCategoryList);
		adapter.notifyDataSetChanged();

	}

	/**
	 * This will refresh the data
	 */
	@Override
	public void reload(long fromMillis, long toMillis) {

		this.fromMillis = fromMillis;
		this.toMillis = toMillis;

		/* Get the current type means which type data is showing and update the the chart with new dates */
		switch (presentType) {
		case TYPE_ALL:
			showAllData();
			break;

		case TYPE_PAID:
			showPaidData();
			break;
		case TYPE_UNPAID:
			showunPaidData();
			break;
		}
	}

}
