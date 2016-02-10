package com.afbb.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the bean class to store the bill data
 */
public class BillBean implements Parcelable {
	private long id;
	private String name;
	private String category;
	private String type;
	private float amount;
	private boolean isRecursive;
	private String recursionPeriod;
	private String startDate;
	private String endDate;
	private String description;

	/**
	 * Default constructor
	 */
	public BillBean() {
	}
	
	

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @return the isRecursive
	 */
	public boolean isRecursive() {
		return isRecursive;
	}

	/**
	 * @return the recursionPeriod
	 */
	public String getRecursionPeriod() {
		return recursionPeriod;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @param isRecursive
	 *            the isRecursive to set
	 */
	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}

	/**
	 * @param recursionPeriod
	 *            the recursionPeriod to set
	 */
	public void setRecursionPeriod(String recursionPeriod) {
		this.recursionPeriod = recursionPeriod;
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

	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeString(category);
		dest.writeString(type);
		dest.writeFloat(amount);
		dest.writeString(recursionPeriod);
		dest.writeString(startDate);
		dest.writeString(endDate);
		dest.writeString(description);
		dest.writeBooleanArray(new boolean[] { isRecursive });
	}

	/**
	 * Parameterized constructor
	 */
	BillBean(Parcel in) {
		id = in.readLong();
		name = in.readString();
		category = in.readString();
		type = in.readString();
		amount = in.readFloat();
		recursionPeriod = in.readString();
		startDate = in.readString();
		endDate = in.readString();
		description=in.readString();

	}

	/**
	 * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<BillBean> CREATOR = new Creator<BillBean>() {

		/**
		 * Create a new array of the Parcelable class.
		 */
		@Override
		public BillBean[] newArray(int size) {
			return new BillBean[size];
		}

		/**
		 * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 */
		@Override
		public BillBean createFromParcel(Parcel source) {
			return new BillBean(source);
		}
	};

}
