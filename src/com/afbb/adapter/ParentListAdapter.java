package com.afbb.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afbb.bean.ChildListBean;
import com.afbb.bean.ParentListBean;
import com.afbb.billmanager.DetailedViewActivity;
import com.afbb.billmanager.R;
import com.afbb.database.BillDatabase;
import com.afbb.fragment.DeleteRecordDialogFragment;
import com.afbb.fragment.ListBillFragment;
import com.afbb.utill.Utility;

/**
 * This is the adapter class for the parent list in the {@link ListBillFragment}
 */
public class ParentListAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String tag = "ParentListAdapter";
	private FragmentActivity activity;
	private ArrayList<ParentListBean> parentListBeans;
	private BillDatabase database;
	private int day;
	private int month;
	private int year;

	/**
	 * @param activity
	 * @param parentListBeans
	 */
	public ParentListAdapter(FragmentActivity activity, ArrayList<ParentListBean> parentListBeans, int day, int month, int year, int listCategory) {
		super();
		this.activity = activity;
		this.parentListBeans = parentListBeans;
		this.day = day;
		this.month = month;
		this.year = year;
		database = new BillDatabase(activity);

		// Show only over due bills
		if (listCategory == ListBillFragment.OVER_DUE) {

			// Remove the bills other than overdue bills
			for (int i = 0; i < parentListBeans.size(); i++) {
				if (!parentListBeans.get(i).getBillType().equals("Overdue Bills")) {
					parentListBeans.remove(i);
					i = 0;
				}
			}
		}

		// Show only upcoming bills
		else if (listCategory == ListBillFragment.UPCOMING) {

			// Remove the bills other than upcoming bills
			for (int i = 0; i < parentListBeans.size(); i++) {
				if (!parentListBeans.get(i).getBillType().equals("Upcoming Bills")) {
					parentListBeans.remove(i);
					i = 0;
				}
			}
		}

		// Show only paid bills
		else if (listCategory == ListBillFragment.PAID) {

			// Remove the bills other than paid bills
			for (int i = 0; i < parentListBeans.size(); i++) {
				if (!parentListBeans.get(i).getBillType().equals("Paid Bills")) {
					parentListBeans.remove(i);
					i = -1;
				}
			}
		}

	}

	/**
	 * Returns the count of items
	 */
	@Override
	public int getCount() {
		return parentListBeans.size();
	}

	/**
	 * Returns the object at position
	 */
	@Override
	public Object getItem(int position) {
		return parentListBeans.get(position);
	}

	/**
	 * Returns the item id at position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Returns view object
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;

		// If view is null
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_parent_list, null);

			holder.billTypeTv = (TextView) view.findViewById(R.id.bill_type_tv);
			holder.numRecordsTv = (TextView) view.findViewById(R.id.num_record_tv);
			holder.payableTv = (TextView) view.findViewById(R.id.payable_tv);
			holder.receivableTv = (TextView) view.findViewById(R.id.receivable_tv);

			holder.childList = (ListView) view.findViewById(R.id.child_Bills_listView);

			view.setTag(holder);
		}

		else {
			holder = (ViewHolder) view.getTag();
		}

		ParentListBean bean = parentListBeans.get(position);

		holder.billTypeTv.setText(bean.getBillType());
		holder.numRecordsTv.setText(bean.getRecords() + " records");
		holder.payableTv.setText("Payable: $" + bean.getPayableAmount());
		holder.receivableTv.setText("Receivable: $" + bean.getReceivableAmount());

		ChildListAdapter adapter = null;
		ArrayList<ChildListBean> childListBeans;
		if (bean.getBillType().equals("Overdue Bills")) {
			childListBeans = database.getOverDueBillsInMonth(day, month, year);
			adapter = new ChildListAdapter(activity, childListBeans);
		}

		else if (bean.getBillType().equals("Upcoming Bills")) {
			childListBeans = database.getUpCommingBills(day, month, year);
			adapter = new ChildListAdapter(activity, childListBeans);
		}

		else if (bean.getBillType().equals("Paid Bills")) {
			childListBeans = database.getPaidBills(month, year);
			adapter = new ChildListAdapter(activity, childListBeans);
		}

		holder.childList.setAdapter(adapter);
		setListViewHeightBasedOnChildren(holder.childList);

		// Register the listener for the child list view
		holder.childList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ChildListBean bean = (ChildListBean) parent.getItemAtPosition(position);
				long itemId = bean.getId();
				Bundle itemBundle = new Bundle();
				itemBundle.putLong("id", itemId);
				Utility.startNextActivity(activity, DetailedViewActivity.class, itemBundle);
			}
		});

		// Register the long click listener
		holder.childList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				DeleteRecordDialogFragment deleteRecordDialogFragment = new DeleteRecordDialogFragment();

				Bundle deleteBundle = new Bundle();
				deleteBundle.putLong("id", id);
				deleteRecordDialogFragment.setArguments(deleteBundle);
				deleteRecordDialogFragment.setTargetFragment(activity.getSupportFragmentManager().findFragmentByTag("fragment"), 20);
				deleteRecordDialogFragment.show(activity.getSupportFragmentManager(), "delete");
				return true;
			}
		});

		return view;
	}

	private class ViewHolder {
		private TextView billTypeTv, numRecordsTv, payableTv, receivableTv;
		private ListView childList;
	}

	/**
	 * This will set the list view height
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
