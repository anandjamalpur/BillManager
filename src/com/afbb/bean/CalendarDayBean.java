package com.afbb.bean;

import java.io.Serializable;

/**
 * This is the bean class to save the day data
 */
public class CalendarDayBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int date;
	int month;
	int year;
	boolean isBill;
	boolean isPaid;

	/**
	 * @return the isBill
	 */
	public boolean isBill() {
		return isBill;
	}

	/**
	 * @param isBill
	 *            the isBill to set
	 */
	public void setBill(boolean isBill) {
		this.isBill = isBill;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @param isPaid
	 *            the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendarDayBean [date=" + date + ", month=" + month + ", year=" + year + ", isBill=" + isBill + ", isPaid=" + isPaid + "]";
	}
	
	

}
