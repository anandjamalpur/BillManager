package com.afbb.utill;

import java.util.HashMap;

import com.afbb.database.BillDatabase;

import android.app.Application;

/**
 * This is the application level singleton class
 */
public class GlobalData extends Application {
	private HashMap<String, String> colorMap;

	/**
	 * This method will map the category name with color
	 */
	public void setColorMap() {
		colorMap = new HashMap<String, String>();
		BillDatabase billDatabase = new BillDatabase(getApplicationContext());
		colorMap = billDatabase.getColorMap();
	}

	/**
	 * This will return the color map
	 * 
	 * @return
	 */
	public HashMap<String, String> getColorMap() {

		return colorMap;
	}

}
