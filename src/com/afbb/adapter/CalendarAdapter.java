package com.afbb.adapter;

import java.util.ArrayList;

import com.afbb.bean.CalendarDayBean;
import com.afbb.billmanager.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is the adapter to show the items in the calendar grid
 */
public class CalendarAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	private ArrayList<CalendarDayBean> daysList;
	private LayoutInflater inflater;

	/**
	 * Parameterized constructor
	 * 
	 * @param context
	 * @param dateList
	 */
	public CalendarAdapter(Context context, ArrayList<CalendarDayBean> dateList) {
		super();
		this.context = context;
		this.daysList = dateList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * This will return the number of items in the grid
	 */
	@Override
	public int getCount() {
		return daysList.size();
	}

	/**
	 * This will return the item at the position
	 */
	@Override
	public Object getItem(int position) {
		return daysList.get(position);
	}

	/**
	 * This will return the id of the item
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * This will create the views for the grid
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// If view is null
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.grid_item, null);

			holder.dateTextView = (TextView) view.findViewById(R.id.dateTextView);
			holder.statusImageView = (ImageView) view.findViewById(R.id.statusImageView);

			view.setTag(holder);

		}

		// If view not null
		else {
			holder = (ViewHolder) view.getTag();
		}

		CalendarDayBean day = daysList.get(position);

		if (day == null) {
			holder.dateTextView.setText("");
		}

		else {
			holder.dateTextView.setText(day.getDate() + "");
			// If it is sun day then
			if (position % 7 == 0) {
				holder.dateTextView.setTextColor(Color.rgb(247, 81, 82));
			}

			// If the day is Saturday
			else if (position % 7 == 6) {
				holder.dateTextView.setTextColor(Color.rgb(33, 109, 222));
			}

			// If the day contains the bill then show the marker
			if (day.isBill()) {
				holder.statusImageView.setVisibility(View.VISIBLE);

				// Check whether the bill is paid or not?
				if (!day.isPaid()) {
					// holder.statusImageView.setBackgroundResource(R.drawable.img_unpaid);
					holder.statusImageView.setImageResource(R.drawable.img_unpaid);
				}
				
				else{
					holder.statusImageView.setImageResource(R.drawable.img_paid);
				}
			}
			// Otherwise hide the marker
			else {
				holder.statusImageView.setVisibility(View.GONE);
			}
		}

		return view;
	}

	/**
	 * View holder pattern
	 */
	private class ViewHolder {
		private TextView dateTextView;
		private ImageView statusImageView;
	}

}
