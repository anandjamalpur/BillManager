package com.afbb.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afbb.billmanager.R;

/**
 * This is the adapter for the list view in the Recurring activity
 */
public class RecurringAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<RecurringBean> recurringBeanList;

	/**
	 * Parameterized constructor
	 * 
	 * @param activity
	 * @param recurringBeanList
	 */
	public RecurringAdapter(Activity activity, ArrayList<RecurringBean> recurringBeanList) {
		super();
		this.activity = activity;
		this.recurringBeanList = recurringBeanList;
	}

	/**
	 * Returns the number of the items in the list view
	 */
	@Override
	public int getCount() {
		return recurringBeanList.size();
	}

	/**
	 * Returns the item at position
	 */
	@Override
	public Object getItem(int position) {
		return recurringBeanList.get(position);
	}

	/**
	 * Returns the item id at position
	 */
	@Override
	public long getItemId(int position) {
		return recurringBeanList.get(position).getBillId();
	}

	/**
	 * Returns the view at position
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// If view is null
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_recurring_list, null);
			holder.billName = (TextView) view.findViewById(R.id.bill_name);
			holder.range = (TextView) view.findViewById(R.id.range_tv);
			holder.description = (TextView) view.findViewById(R.id.desc_tv);
			holder.amount = (TextView) view.findViewById(R.id.bill_amount_tv);
			holder.period = (TextView) view.findViewById(R.id.period_tv);

			view.setTag(holder);
		}

		// If view is not null
		else {
			holder = (ViewHolder) view.getTag();
		}

		RecurringBean bean = recurringBeanList.get(position);
		holder.billName.setText(bean.getName() + " : " + bean.getCategory());
		holder.range.setText(bean.getStartWeek() + ", " + bean.getStartDate() + " - " + bean.getEndWeek() + ", " + bean.getEndDate());

		// If the description is present then show it
		if (bean.getDescription() != null && bean.getDescription().length() > 0) {
			holder.description.setVisibility(View.VISIBLE);
			holder.description.setText(bean.getDescription());
		}

		// Otherwise make invisible the description text view
		else {
			holder.description.setVisibility(View.GONE);
		}

		holder.amount.setText("$" + bean.getAmount());
		holder.period.setText(bean.getRecursionPeriod());

		return view;
	}

	/**
	 * View holder pattern
	 */
	private class ViewHolder {
		TextView billName, range, description, amount, period;
	}

}
