package com.afbb.fragment;

/**
 * This is interface to communicate between the dialog and activity
 */
public interface AddBillActivityCommunicator {


	/**
	 * This will update the spinner in the attached activity
	 */
//	public void updateSpinner(String categoryName);

	/**
	 * This will set the date to the activity
	 * 
	 * @param day
	 * @param month
	 * @param year
	 */
	public void setDate(int day, int month, int year, int id);
}