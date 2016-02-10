package com.afbb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.afbb.billmanager.DetailedViewActivity;

/**
 * This is the bean class to to hold the data which is used in {@link DetailedViewActivity}
 */
public class DetailBean implements Parcelable {
	private String name, type, category, period, startDate, endDate, description;
	private boolean isRecursion;
	private float amount;

	/**
	 * Default constructor
	 */
	public DetailBean() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
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
	 * @return the isRecursion
	 */
	public boolean isRecursion() {
		return isRecursion;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
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
	 * @param isRecursion
	 *            the isRecursion to set
	 */
	public void setRecursion(boolean isRecursion) {
		this.isRecursion = isRecursion;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
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

		// private String name, type, category, period, startDate, endDate, description;
		// private boolean isRecursion;
		// private float amount;
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(category);
		dest.writeString(period);
		dest.writeString(startDate);
		dest.writeString(endDate);
		dest.writeString(description);
		dest.writeFloat(amount);
		dest.writeValue(isRecursion);

	}

	DetailBean(Parcel in) {
		name = in.readString();
		type = in.readString();
		category = in.readString();
		period = in.readString();
		startDate = in.readString();
		endDate = in.readString();
		description = in.readString();
		amount = in.readFloat();
		isRecursion = (Boolean) in.readValue(null);
	}

	/**
	 * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<DetailBean> CREATOR = new Creator<DetailBean>() {

		/**
		 * Create a new array of the Parcelable class.
		 */
		@Override
		public DetailBean[] newArray(int size) {
			return new DetailBean[size];
		}

		/**
		 * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 */
		@Override
		public DetailBean createFromParcel(Parcel source) {
			return new DetailBean(source);
		}
	};

}
