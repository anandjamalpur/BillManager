package com.afbb.utill;

/**
 * This is the exception class to handle the exceptions occurred in the application
 */
public class BillException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public BillException() {
		super();
	}

	/**
	 * Parameterized constructor to the message to the user
	 */
	public BillException(String detailMessage) {
		super(detailMessage);
	}

}
