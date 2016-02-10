package com.afbb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afbb.billmanager.CategoriesActivity;
import com.afbb.billmanager.R;
import com.afbb.utill.Utility;

/**
 * This is the fragment which provides the interface set the application settings
 */
public class MoreFragment extends Fragment {

	private static final String tag = "MoreFragment";
	private TextView categoriesTextView;

	/**
	 * Inflate the layout
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_more, null);

		return view;
	}

	/**
	 * Initialize the views
	 */
	@Override
	public void onStart() {
		super.onStart();
		categoriesTextView = (TextView) getView().findViewById(R.id.category_textView);

		// Register the listeners here.
		registerListeners();
		Log.d(tag, categoriesTextView.getText().toString());
	}

	/**
	 * Register the listeners
	 */
	private void registerListeners() {
		categoriesTextView.setOnClickListener(new ClicikListener());
	}

	/**
	 * This is the listener class for the registered views in the fragment
	 */
	public class ClicikListener implements OnClickListener {

		/**
		 * Call back method
		 */
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			// Show the CategoriesActivity
			case R.id.category_textView:
				Utility.startNextActivity(getActivity(), CategoriesActivity.class);
				break;
			}
		}

	}

}
