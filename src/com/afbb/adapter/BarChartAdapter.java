package com.afbb.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afbb.bean.Category;
import com.afbb.billmanager.R;

/**
 * This is the adapter for the list in the bar chart fragment
 */
public class BarChartAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Category> categoryList;
	private int screenWidth;
	private float sumOfAmount;

	/**
	 * Parameterized constructor
	 * 
	 * @param activity
	 * @param categoryList
	 */
	public BarChartAdapter(Activity activity, ArrayList<Category> categoryList) {
		super();
		this.activity = activity;
		this.categoryList = categoryList;

		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		screenWidth = dm.widthPixels;

		sumOfAmount = 0;
		// Find the sum of amount
		for (Category category : categoryList) {
			sumOfAmount = sumOfAmount + category.getCategoryAmount();
		}


	}

	/**
	 * Returns the count of the items in list
	 */
	@Override
	public int getCount() {
		return categoryList.size();
	}

	/**
	 * Returns the item at position
	 */
	@Override
	public Object getItem(int position) {
		return categoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Returns the view at position
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// If view is empty
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_bar_chart, null);
			holder.category = (TextView) view.findViewById(R.id.categoryTextView);
			holder.progress = (TextView) view.findViewById(R.id.progressTextView);
			view.setTag(holder);
		}

		// If view is not null
		else {
			holder = (ViewHolder) view.getTag();
		}

		Category category = categoryList.get(position);
		holder.category.setText(category.getCategoryName() + " $" + category.getCategoryAmount() + " (" + percent((int) category.getCategoryAmount()) + "%)");
		holder.category.setTextColor(Color.parseColor(category.getCategoryColor()));

		holder.progress.setWidth(getTheWidthOfProgress(category.getCategoryAmount()));
		holder.progress.setBackgroundColor(Color.parseColor(category.getCategoryColor()));

		return view;
	}

	@Override
	public void notifyDataSetChanged() {

		sumOfAmount = 0;
		// Find the sum of amount
		for (Category category : categoryList) {
			sumOfAmount = sumOfAmount + category.getCategoryAmount();
		}

		super.notifyDataSetChanged();
	}

	/**
	 * This method will return the width of the text view to be set
	 */
	private int getTheWidthOfProgress(float amount) {

		int width = (int) ((screenWidth / sumOfAmount) * amount);

		return width;

	}

	/**
	 * This will return the percent of the category amount
	 */
	private int percent(int amount) {
		int percent = (int) ((amount / sumOfAmount) * 100);
		return percent;
	}

	/**
	 * View holder pattern
	 */
	private class ViewHolder {
		TextView category, progress;
	}

}
