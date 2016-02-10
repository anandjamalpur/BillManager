package com.afbb.billmanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.afbb.adapter.CategoryListAdapter;
import com.afbb.bean.Category;
import com.afbb.database.BillDatabase;
import com.afbb.fragment.AddCategoryDialogFragment;
import com.afbb.fragment.AddCategoryDialogFragment.CategoriesCommunicator;

/**
 * This is the activity which will provide the interface to manage categories
 */
public class CategoriesActivity extends FragmentActivity implements OnClickListener, CategoriesCommunicator {

	private ListView categoryListView;

	private CategoryListAdapter categoryListAdapter;

	private ArrayList<Category> categoryList;

	private BillDatabase billDatabase;

	private ImageView addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_categories);

		categoryListView = (ListView) findViewById(R.id.categoriesListView);
		billDatabase = new BillDatabase(this);
		addButton = (ImageView) findViewById(R.id.add_button);

		addButton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		categoryList = billDatabase.getCategoriesList();
		categoryListAdapter = new CategoryListAdapter(categoryList, this);
		categoryListView.setAdapter(categoryListAdapter);
		categoryListView.setOnItemLongClickListener(new ListListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}

	/**
	 * This method is called when the add button is clicked
	 */
	@Override
	public void onClick(View view) {
		AddCategoryDialogFragment fragment = new AddCategoryDialogFragment();
		fragment.show(getSupportFragmentManager(), "add");
	}

	/**
	 * This will update the list view
	 */
	@Override
	public void updateList() {
		ArrayList<Category> tempCategoryList = billDatabase.getCategoriesList();
		categoryList.clear();
		categoryList.addAll(tempCategoryList);
		categoryListAdapter.notifyDataSetChanged();
	}

	/**
	 * This is the listener class for the list click events
	 */
	public class ListListener implements OnItemLongClickListener {

		/**
		 * This is the list long click listener
		 */
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

			// Category category=(Category) parent.getItemAtPosition(position);
			// billDatabase.deleteCategory(category.getCategoryName());
			// updateList();
			return true;
		}

	}

}
