package com.afbb.fragment;

/**
 * This is the interface to communicate between the Reports activity and its fragments
 */
public interface FragmentComunicator {

	public static final int TYPE_PAID = 0;
	public static final int TYPE_UNPAID = 1;
	public static final int TYPE_ALL = 3;

	/**
	 * This will show total data in the chart
	 */
	public void showAllData();

	/**
	 * This will show paid data in the chart
	 */
	public void showPaidData();

	/**
	 * This will show unpaid data in the chart
	 */
	public void showunPaidData();

	/**
	 * This will refresh the data
	 */
	public void reload(long fromMillis, long toMillis);
}
