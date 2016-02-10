package com.afbb.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afbb.bean.ChildListBean;
import com.afbb.billmanager.HomeActivity;
import com.afbb.billmanager.R;
import com.afbb.database.BillDatabase;
import com.afbb.utill.GlobalData;

/**
 * This is the adapter class for the items in the child list
 */
public class ChildListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<ChildListBean> arrayList;
	private BillDatabase database;
	private HashMap<String, String> colorMap;

	/**
	 * @param activity
	 * @param arrayList
	 */
	public ChildListAdapter(Activity activity, ArrayList<ChildListBean> arrayList) {
		super();
		this.activity = activity;
		this.arrayList = arrayList;
		database = new BillDatabase(activity);
		colorMap = ((GlobalData) activity.getApplication()).getColorMap();
	}

	/**
	 * Returns the count of items
	 */
	@Override
	public int getCount() {
		return arrayList.size();
	}

	/**
	 * Returns the object at position
	 */
	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	/**
	 * Returns the item id at position
	 */
	@Override
	public long getItemId(int position) {
		return arrayList.get(position).getId();
	}

	/**
	 * Returns view object
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_child_list, null);

			holder.billNameTv = (TextView) view.findViewById(R.id.bill_name_tv);
			holder.billAmountTv = (TextView) view.findViewById(R.id.bill_amount_tv);
			holder.billDescTV = (TextView) view.findViewById(R.id.bill_desc_tv);
			holder.statusTv = (TextView) view.findViewById(R.id.statusTextView);
			holder.dateTv = (TextView) view.findViewById(R.id.dateTv);
			holder.weekTv = (TextView) view.findViewById(R.id.weekTv);
			holder.dateLayout = (RelativeLayout) view.findViewById(R.id.dateLayout);

			view.setTag(holder);

		}

		else {
			holder = (ViewHolder) view.getTag();
		}
		final ChildListBean bean = arrayList.get(position);

		holder.billNameTv.setText(bean.getName());
		holder.billAmountTv.setText("$" + bean.getAmount());

		// Set the category color to the text view back ground
		holder.dateTv.setText(bean.getDay() + "");
		holder.weekTv.setText(bean.getWeek());

		holder.dateLayout.setBackgroundColor(Color.parseColor(colorMap.get(bean.getCategory())));

		// If bill is paid then set status as paid
		if (bean.isPaid()) {
			holder.billDescTV.setText("Paid on " + bean.getPaidDate());
			holder.statusTv.setText("");

			holder.statusTv.setTextColor(Color.BLACK);
			holder.statusTv.setBackgroundColor(Color.TRANSPARENT);
		}
		// If bill is not paid then the make text view "Mark as paid"
		else {
			holder.billDescTV.setText(bean.getDesc());
			holder.statusTv.setText("Mark as Paid");

			holder.statusTv.setBackgroundResource(R.drawable.pay_btn_shape);
			holder.statusTv.setTextColor(Color.WHITE);

			holder.statusTv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					database.updateBillRecordById(bean.getId());
					((HomeActivity) activity).updateListView();

				}
			});
		}

		return view;
	}

	/**
	 *View holder patter
	 */
	private class ViewHolder {
		private TextView billNameTv, billAmountTv, billDescTV, statusTv, dateTv, weekTv;
		private RelativeLayout dateLayout;
	}

}
