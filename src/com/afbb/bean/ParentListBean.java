package com.afbb.bean;

import java.io.Serializable;

/**
 * This is the bean class to store the parent list item
 */
public class ParentListBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String billType;
	int records;
	float payableAmount;
	float receivableAmount;

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @return the records
	 */
	public int getRecords() {
		return records;
	}

	/**
	 * @return the payableAmount
	 */
	public float getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @return the receivableAmount
	 */
	public float getReceivableAmount() {
		return receivableAmount;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(int records) {
		this.records = records;
	}

	/**
	 * @param payableAmount
	 *            the payableAmount to set
	 */
	public void setPayableAmount(float payableAmount) {
		this.payableAmount = payableAmount;
	}

	/**
	 * @param receivableAmount
	 *            the receivableAmount to set
	 */
	public void setReceivableAmount(float receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

}
