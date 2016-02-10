package com.afbb.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afbb.bean.HomeListBean;
import com.afbb.billmanager.HomeActivity;
import com.afbb.billmanager.R;
import com.afbb.database.BillDatabase;
import com.afbb.utill.Utility;

/**
 * This is the adapter class for the list view in the home screen
 */
public class HomeListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HomeListBean> beanList;
	private LayoutInflater inflater;
	private BillDatabase billDatabase;

	/**
	 * Parameterized constructor
	 * 
	 * @param activity
	 * @param beanList
	 */
	public HomeListAdapter(Activity activity, ArrayList<HomeListBean> beanList) {
		super();
		this.activity = activity;
		this.beanList = beanList;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		billDatabase = new BillDatabase(activity);
	}

	/**
	 * Returns the count of items in the list view
	 */
	@Override
	public int getCount() {
		return beanList.size();
	}

	/**
	 * Returns the item at the position
	 */
	@Override
	public Object getItem(int position) {
		return beanList.get(position);
	}

	/**
	 * Returns the item id at the position
	 */
	@Override
	public long getItemId(int position) {
		return beanList.get(position).getRecordId();
	}

	/**
	 * Returns the view at the position
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// If view is null
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.item_today_list, null);

			holder.billNameTv = (TextView) view.findViewById(R.id.billNameTextView);
			holder.billAmountTv = (TextView) view.findViewById(R.id.amountTextView);
			holder.paidDateTV = (TextView) view.findViewById(R.id.paidDateTextView);
			holder.statusTv = (TextView) view.findViewById(R.id.statusTextView);

			view.setTag(holder);
		}

		// If view is not null
		else {
			holder = (ViewHolder) view.getTag();
		}

		HomeListBean bean = beanList.get(position);

		holder.billNameTv.setText(bean.getBillName() + " : " + bean.getCategory());
		holder.billAmountTv.setText("$" + bean.getAmount());

		// If bill is paid
		if (bean.isPaid()) {
			holder.statusTv.setText("PAID");
			holder.paidDateTV.setText("Paid on " + bean.getPaidDate());
			holder.statusTv.setTextColor(Color.BLACK);
			holder.statusTv.setBackgroundColor(Color.TRANSPARENT);
		}

		// If bill is not paid
		else {
			holder.statusTv.setText("Mark as Paid");
			holder.statusTv.setBackgroundResource(R.drawable.pay_btn_shape);
			holder.statusTv.setTextColor(Color.WHITE);

			holder.paidDateTV.setText("");

			// Register the listener for the text view and set the id as tag
			holder.statusTv.setTag(beanList.get(position));
			holder.statusTv.setOnClickListener(new StatusListener());
			holder.paidDateTV.setText(bean.getDescription());
		}

		return view;
	}

	/**
	 * This is the listener class for the status text view
	 */
	public class StatusListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			HomeListBean bean = (HomeListBean) view.getTag();

			long recordId = bean.getRecordId();


			billDatabase.updateBillRecordById(recordId);

			String date = bean.getDate();
			long timeMillis = Utility.convertDateToMillis(date);
			// Update the list view
			ArrayList<HomeListBean> tempHomeListBeans = billDatabase.getBillsByDay(Utility.getDayFromMillis(timeMillis),
					Utility.getMonthFromMillis(timeMillis), Utility.getYearFromMillis(timeMillis));
			beanList.clear();
			beanList.addAll(tempHomeListBeans);
			notifyDataSetChanged();

			// Now update the calendar grid
			((HomeActivity) activity).updateGrid();
		}

	}

	/**
	 * View holder pattern
	 */
	private class ViewHolder {
		private TextView billNameTv, billAmountTv, paidDateTV, statusTv;
	}

}
