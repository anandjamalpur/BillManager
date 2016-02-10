package com.afbb.billmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.afbb.bean.DetailBean;
import com.afbb.database.BillDatabase;

/**
 * This activity is for showing the details of the bills0
 */
public class DetailedViewActivity extends Activity {

	// Declare the variables
	private TextView nameTv, typeTv, categoryTv, recursionTv, periodTv, startDateTv, endDateTv, descriptionTv, lablePeriodTv, lableStartDateTv,labelEndDateTv;
	private long itemId;
	private BillDatabase billDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detailed_view);

		// Initialize the variables
		init();
	}

	/**
	 * Initialize the views
	 */
	private void init() {
		nameTv = (TextView) findViewById(R.id.name_textView);
		typeTv = (TextView) findViewById(R.id.type_textView);
		categoryTv = (TextView) findViewById(R.id.category_textView);
		recursionTv = (TextView) findViewById(R.id.recursion_textView);
		periodTv = (TextView) findViewById(R.id.period_textView);
		startDateTv = (TextView) findViewById(R.id.start_date_textView);
		endDateTv = (TextView) findViewById(R.id.end_date_textView);
		descriptionTv = (TextView) findViewById(R.id.desc_textView);
		lablePeriodTv = (TextView) findViewById(R.id.label_recursion_period_textView);
		lableStartDateTv = (TextView) findViewById(R.id.label_start_date_textView);
		labelEndDateTv=(TextView) findViewById(R.id.label_end_date_textView);

		billDatabase = new BillDatabase(this);

	}

	/**
	 * Get the data from the database and assign data to text views
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Bundle dataBundle = getIntent().getExtras();
		itemId = dataBundle.getLong("id");
		DetailBean bean = billDatabase.getBillById(itemId);

		nameTv.setText(bean.getName());
		typeTv.setText(bean.getType());
		categoryTv.setText(bean.getCategory());
		descriptionTv.setText(bean.getDescription());

		// If the bill is recursion bill
		if (bean.isRecursion()) {
			recursionTv.setText("Yes");
			periodTv.setText(bean.getPeriod());
			startDateTv.setText(bean.getStartDate());
			endDateTv.setText(bean.getEndDate());
		}

		else {
			recursionTv.setText("No");
			periodTv.setVisibility(View.GONE);
			lablePeriodTv.setVisibility(View.GONE);
			startDateTv.setVisibility(View.GONE);
			lableStartDateTv.setVisibility(View.GONE);
			labelEndDateTv.setText("Bill Date");
			endDateTv.setText(bean.getStartDate());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_view, menu);
		return true;
	}

}
