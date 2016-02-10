package com.afbb.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the bean class to store the categories data
 */
public class Category implements Parcelable {
	private String categoryName;
	private String categoryColor;
	private float categoryAmount;

	/**
	 * Default constructor
	 */
	public Category() {
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @return the categoryColor
	 */
	public String getCategoryColor() {
		return categoryColor;
	}

	/**
	 * @return the categoryAmount
	 */
	public float getCategoryAmount() {
		return categoryAmount;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @param categoryColor
	 *            the categoryColor to set
	 */
	public void setCategoryColor(String categoryColor) {
		this.categoryColor = categoryColor;
	}

	/**
	 * @param categoryAmount
	 *            the categoryAmount to set
	 */
	public void setCategoryAmount(float categoryAmount) {
		this.categoryAmount = categoryAmount;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(categoryName);
		dest.writeString(categoryColor);
		dest.writeFloat(categoryAmount);
	}

	/**
	 * Parameterized constructor
	 * 
	 * @param in
	 */
	Category(Parcel in) {
		categoryName=in.readString();
		categoryColor=in.readString();
		categoryAmount=in.readFloat();
	}
	
	/**
	 * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<Category> CREATOR = new Creator<Category>() {

		/**
		 * Create a new array of the Parcelable class.
		 */
		@Override
		public Category[] newArray(int size) {
			return new Category[size];
		}

		/**
		 * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by
		 * Parcelable.writeToParcel().
		 */
		@Override
		public Category createFromParcel(Parcel source) {
			return new Category(source);
		}
	};
}
