package com.afbb.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afbb.bean.Category;
import com.afbb.billmanager.R;

/**
 * This is the adapter for the list view in the {@link CategoryListFragment}
 */
public class CategoryListAdapter extends BaseAdapter {

	private ArrayList<Category> categoryList;

	private LayoutInflater inflater;

	/**
	 * Parameterized constructor
	 * 
	 * @param categoryList
	 * @param activity
	 */
	public CategoryListAdapter(ArrayList<Category> categoryList, Activity activity) {
		super();
		this.categoryList = categoryList;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Returns the count of the items in the list view
	 */
	@Override
	public int getCount() {
		return categoryList.size();
	}

	/**
	 * Returns the item at the position
	 */
	@Override
	public Object getItem(int position) {
		return categoryList.get(position);
	}

	/**
	 * Returns the item id at the position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Constructs the view at position and returns it
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// Inflate the view
		if (view == null) {
			holder = new ViewHolder();

			view = inflater.inflate(R.layout.item_category, null);

			// Initialize the views
			holder.colorTv = (TextView) view.findViewById(R.id.category_color);
			holder.categoryTv = (TextView) view.findViewById(R.id.category_name_tv);

			view.setTag(holder);
		}

		// Otherwise initialize the holder
		else {
			holder = (ViewHolder) view.getTag();
		}

		Category category = categoryList.get(position);
		holder.categoryTv.setText(category.getCategoryName());
		holder.colorTv.setBackgroundColor(Color.parseColor(category.getCategoryColor()));

		return view;
	}

	/**
	 * View holder pattern
	 */
	private class ViewHolder {
		private TextView colorTv, categoryTv;
	}

}
