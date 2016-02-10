package com.afbb.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the bean class to store the home list data
 */
public class HomeListBean implements Parcelable {
	private long recordId;

	private String billName;
	private float amount;
	private String date;
	private boolean isPaid;
	private String paidDate;
	private String category;
	private String description;

	/**
	 * Default constructor
	 */
	public HomeListBean() {
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * @return the paidDate
	 */
	public String getPaidDate() {
		return paidDate;
	}

	/**
	 * @param isPaid
	 *            the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * @param paidDate
	 *            the paidDate to set
	 */
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	/**
	 * @return the recordId
	 */
	public long getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the billName
	 */
	public String getBillName() {
		return billName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param billName
	 *            the billName to set
	 */
	public void setBillName(String billName) {
		this.billName = billName;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Flatten this object in to a Parcel.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(billName);
		dest.writeString(date);
		dest.writeString(paidDate);
		dest.writeString(category);
		dest.writeString(description);
		dest.writeFloat(amount);
		dest.writeValue(isPaid);
	}

	/**
	 * Parameterized Constructor
	 */
	HomeListBean(Parcel in) {
		billName = in.readString();
		date = in.readString();
		paidDate = in.readString();
		category = in.readString();
		description = in.readString();
		amount = in.readFloat();
		isPaid = (Boolean) in.readValue(null);
	}

	/**
	 * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<HomeListBean> CREATOR = new Creator<HomeListBean>() {

		/**
		 * Create a new array of the Parcelable class.
		 */
		@Override
		public HomeListBean[] newArray(int size) {
			return new HomeListBean[size];
		}

		/**
		 * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 */
		@Override
		public HomeListBean createFromParcel(Parcel source) {
			return new HomeListBean(source);
		}
	};

}
