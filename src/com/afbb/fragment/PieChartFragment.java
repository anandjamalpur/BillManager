package com.afbb.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afbb.bean.Category;
import com.afbb.billmanager.R;
import com.afbb.billmanager.ReportsActivity;
import com.afbb.customview.PieChart;
import com.afbb.database.BillDatabase;

/**
 * This is the fragment which contains the pie chart
 */
public class PieChartFragment extends Fragment implements FragmentComunicator {

	private PieChart pieChart;
	private BillDatabase billDatabase;
	private long toMillis;
	private long fromMillis;
	private int presentType = -1;

	/**
	 * Instantiate the activity methods
	 */
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

		View pieChartLayout = inflater.inflate(R.layout.fragment_piechart, container, false);
		presentType = TYPE_ALL;

		pieChart = (PieChart) pieChartLayout.findViewById(R.id.pieChart);

		// Restore the fromMillis and restore toMillis
		if (savedInstanceState != null) {
			fromMillis = savedInstanceState.getLong("from");
			toMillis = savedInstanceState.getLong("to");
			presentType = savedInstanceState.getInt("type");
		}

		return pieChartLayout;
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

		reload(fromMillis, toMillis);

	}

	/**
	 * This will show total data in the chart
	 */
	@Override
	public void showAllData() {
		presentType = TYPE_ALL;
		pieChart.reFresh();
		// Get the data from the database
		ArrayList<Category> categoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_ALL);
		for (Category category : categoryList) {
			pieChart.addCategories(category.getCategoryName(), category.getCategoryAmount(), category.getCategoryColor());
		}
		pieChart.reDraw();
	}

	/**
	 * This will show paid data in the chart
	 */
	@Override
	public void showPaidData() {
		presentType = TYPE_PAID;
		pieChart.reFresh();
		// Get the data from the database
		ArrayList<Category> categoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_PAID);
		for (Category category : categoryList) {
			pieChart.addCategories(category.getCategoryName(), category.getCategoryAmount(), category.getCategoryColor());
		}
		pieChart.reDraw();
	}

	/**
	 * This will show unpaid data in the chart
	 */
	@Override
	public void showunPaidData() {
		presentType = TYPE_UNPAID;
		pieChart.reFresh();
		// Get the data from the database
		ArrayList<Category> categoryList = billDatabase.getTheCategoryWiseData(fromMillis, toMillis, TYPE_UNPAID);
		for (Category category : categoryList) {
			pieChart.addCategories(category.getCategoryName(), category.getCategoryAmount(), category.getCategoryColor());
		}
		pieChart.reDraw();
	}

	/**
	 * This will refresh the data
	 */
	@Override
	public void reload(long fromMillis, long toMillis) {

		this.fromMillis = fromMillis;
		this.toMillis = toMillis;

		/* Get the current type means which type data is showing and update the the bar chart with new dates */
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
