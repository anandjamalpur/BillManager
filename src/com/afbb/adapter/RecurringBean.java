package com.afbb.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the bean class to store the recurring data
 */
public class RecurringBean implements Parcelable {
	long billId;
	String name, category, startDate, endDate, description, recursionPeriod, startWeek, endWeek;
	float amount;

	/**
	 * Default constructor
	 */
	public RecurringBean() {
	}

	/**
	 * @return the startWeek
	 */
	public String getStartWeek() {
		return startWeek;
	}

	/**
	 * @return the endWeek
	 */
	public String getEndWeek() {
		return endWeek;
	}

	/**
	 * @param startWeek
	 *            the startWeek to set
	 */
	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}

	/**
	 * @param endWeek
	 *            the endWeek to set
	 */
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}

	/**
	 * @return the billId
	 */
	public long getBillId() {
		return billId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the recursionPeriod
	 */
	public String getRecursionPeriod() {
		return recursionPeriod;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param billId
	 *            the billId to set
	 */
	public void setBillId(long billId) {
		this.billId = billId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param recursionPeriod
	 *            the recursionPeriod to set
	 */
	public void setRecursionPeriod(String recursionPeriod) {
		this.recursionPeriod = recursionPeriod;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Flatten this object in to a Parcel.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(billId);
		dest.writeString(name);
		dest.writeString(category);
		dest.writeString(startDate);
		dest.writeString(endDate);
		dest.writeString(description);
		dest.writeString(recursionPeriod);
		dest.writeFloat(amount);
		dest.writeString(startWeek);
		dest.writeString(endWeek);
	}

	RecurringBean(Parcel in) {
		billId = in.readLong();
		name = in.readString();
		category = in.readString();
		startDate = in.readString();
		endDate = in.readString();
		description = in.readString();
		recursionPeriod = in.readString();
		amount = in.readFloat();
		startWeek=in.readString();
		endWeek=in.readString();
	}

	/**
	 * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<RecurringBean> CREATOR = new Creator<RecurringBean>() {

		/**
		 * Create a new array of the Parcelable class.
		 */
		@Override
		public RecurringBean[] newArray(int size) {
			return new RecurringBean[size];
		}

		/**
		 * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 */
		@Override
		public RecurringBean createFromParcel(Parcel source) {
			return new RecurringBean(source);
		}
	};
}
