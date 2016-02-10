package com.afbb.utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.afbb.bean.BillBean;
import com.afbb.bean.CalendarDayBean;
import com.afbb.database.BillDatabase;

/**
 * This is the utility class contains the common reusable methods
 */
public class Utility {

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

	/**
	 * This method will fill the array list with dates data
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static ArrayList<CalendarDayBean> fillList(int month, int year, Context context) {

		ArrayList<Integer> listOfDaysContainsBills = getBillDaysInMonth(month, year, context);
		ArrayList<Integer> unPaidListofBills = getUnpaidBillDaysInMonth(month, year, context);

		ArrayList<CalendarDayBean> daysList = new ArrayList<CalendarDayBean>(42);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DATE, 1);

		// Now find the first day week of the month
		int firstDayWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// For the weeks before 1st date insert null
		for (int i = 1; i < firstDayWeek; i++) {
			daysList.add(null);
		}

		// Now add the remaining objects
		int i = 1;
		while (i <= calendar.getActualMaximum(Calendar.DATE)) {
			CalendarDayBean day = new CalendarDayBean();
			day.setDate(i);
			day.setMonth(month);
			day.setYear(year);

			// Check whether the day contains any bills
			if (listOfDaysContainsBills.contains(i)) {
				day.setBill(true);

				// If bill contains check whether it is paid or unpaid
				if (unPaidListofBills.contains(i)) {
					day.setPaid(false);
				}

				// If paid
				else {
					day.setPaid(true);
				}
			}
			daysList.add(day);

			i++;
		}

		// Now add null to fill the 42 elements
		int remainingElemntsCount = 42 - daysList.size();
		for (int j = 0; j < remainingElemntsCount; j++) {
			daysList.add(null);
		}

		return daysList;
	}

	/**
	 * This will get the list of dates which contains the bills
	 */
	private static ArrayList<Integer> getBillDaysInMonth(int month, int year, Context context) {
		BillDatabase billDatabase = new BillDatabase(context);
		ArrayList<Integer> billDaysList = billDatabase.getBillDaysInMonth(month, year);
		return billDaysList;
	}

	/**
	 * This will return the list of dates which contains unpaid bills in the month of year
	 */
	private static ArrayList<Integer> getUnpaidBillDaysInMonth(int month, int year, Context context) {
		BillDatabase billDatabase = new BillDatabase(context);
		ArrayList<Integer> unPaidBillDaysList = billDatabase.getUnpaidBillsDaysInMonth(month, year);
		return unPaidBillDaysList;
	}

	/**
	 * This method will return the month name if you give the number of the month based on {@link Calendar} class
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month) {

		String monthStr = "";

		switch (month) {
		case 0:
			monthStr = "Jan";
			break;
		case 1:
			monthStr = "Feb";
			break;
		case 2:
			monthStr = "Mar";
			break;
		case 3:
			monthStr = "Apr";
			break;
		case 4:
			monthStr = "May";
			break;
		case 5:
			monthStr = "Jun";
			break;
		case 6:
			monthStr = "Jul";
			break;
		case 7:
			monthStr = "Aug";
			break;
		case 8:
			monthStr = "Sep";
			break;
		case 9:
			monthStr = "Oct";
			break;
		case 10:
			monthStr = "Nov";
			break;
		case 11:
			monthStr = "Dec";
			break;

		}

		return monthStr;
	}

	/**
	 * This method returns week of the date provided
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static String getWeekName(int day, int month, int year) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

		return getWeekString(calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * This method returns week of the date provided
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static String getWeekShortName(int day, int month, int year) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

		return getWeekShortString(calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * This method will return the name of the week based on week id
	 * 
	 * @param weekId
	 * @return
	 */
	private static String getWeekString(int weekId) {
		String weekName = "";

		switch (weekId) {
		case 1:
			weekName = "Sunday";
			break;
		case 2:
			weekName = "Monday";
			break;
		case 3:
			weekName = "Tuesday";
			break;
		case 4:
			weekName = "Wednesday";
			break;
		case 5:
			weekName = "Thursday";
			break;
		case 6:
			weekName = "Friday";
			break;
		case 7:
			weekName = "Saturday";
			break;
		}

		return weekName;
	}

	/**
	 * This method will return the short name of the week based on week id
	 * 
	 * @param weekId
	 * @return
	 */
	private static String getWeekShortString(int weekId) {
		String weekName = "";

		switch (weekId) {
		case 1:
			weekName = "Sun";
			break;
		case 2:
			weekName = "Mon";
			break;
		case 3:
			weekName = "Tue";
			break;
		case 4:
			weekName = "Wed";
			break;
		case 5:
			weekName = "Thu";
			break;
		case 6:
			weekName = "Fri";
			break;
		case 7:
			weekName = "Sat";
			break;
		}

		return weekName;
	}

	/**
	 * This method will convert the date to milli seconds
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long convertDateToMillis(String dateStr) {

		try {
			Date date = dateFormat.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			return calendar.getTimeInMillis();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * This method will convert the milli seconds to date
	 * 
	 * @param millis
	 * @return
	 */
	public static String convertMillisToDate(long millis) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);

		String dateStr = dateFormat.format(calendar.getTime());
		return dateStr;
	}

	/**
	 * This method will return the dates between the start date and end date in recursion period intervals
	 * 
	 * @param recursionPeriod
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static ArrayList<String> getDatesList(String recursionPeriod, String startDate, String endDate) {
		ArrayList<String> dateList = new ArrayList<String>();

		// Get start date
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTimeInMillis(convertDateToMillis(startDate));

		// Get end date
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTimeInMillis(convertDateToMillis(endDate));

		while (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {

			// If recursion type is daily
			if (recursionPeriod.equals("Daily")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.DATE, 1);
			}
			// If recursion type is weekly
			else if (recursionPeriod.equals("Weekly")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.DATE, 7);
			}

			// If recursion type is monthly
			else if (recursionPeriod.equals("Monthly")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.MONTH, 1);
			}

			// If recursion type is quarterly
			else if (recursionPeriod.equals("Quarterly")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.MONTH, 3);
			}

			// If recursion type is half yearly
			else if (recursionPeriod.equals("Half Yearly")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.MONTH, 6);
			}

			// If recursion type is yearly
			else if (recursionPeriod.equals("Yearly")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.YEAR, 1);
			}

			// If recursion type is none
			else if (recursionPeriod.equals("once")) {
				dateList.add(convertMillisToDate(startCalendar.getTimeInMillis()));
				startCalendar.add(Calendar.YEAR, 1);
			}

		}

		return dateList;
	}

	/**
	 * This will return the day of the date
	 * 
	 * @param millis
	 * @return
	 */
	public static int getDayFromMillis(long millis) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * This will return the month of the date
	 * 
	 * @param millis
	 * @return
	 */
	public static int getMonthFromMillis(long millis) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * This will return the day of the date
	 * 
	 * @param millis
	 * @return
	 */
	public static int getYearFromMillis(long millis) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * This method will return the name of the week
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getWeekNameFromDateString(String dateStr) {
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

			Date date = dateFormat.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			return getWeekShortName(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * This will validate the inputs taken from the user
	 * 
	 * @param billBean
	 * @throws BillException
	 * @throws ParseException
	 */
	public static void validateInputs(BillBean billBean) throws BillException, ParseException {

		// Validate name
		if (billBean.getName().length() == 0) {
			throw new BillException("Enter the bill name");
		}

		// validate bill amount
		float amount = billBean.getAmount();
		String amountStr = amount + "";

		String amtArr[] = amountStr.split("\\.");

		// If the decimal part is greater than 6 then throw exception
		if (amtArr[0].length() > 6) {
			throw new BillException("Invalid bill amount");
		}

		// If the fraction part length is greater than 2 then throw exception
		if (amtArr.length > 1 && amtArr[1].length() > 2) {
			throw new BillException("Invalid fraction amount. Please enter only two digits after dot");
		}

		// Validate the date
		if (billBean.getStartDate().length() == 0) {
			throw new BillException("Enter the bill date");
		}

		// Validate the end date
		if (billBean.getEndDate().length() == 0) {
			throw new BillException("Enter the end date");
		}

		// Validate the dates range
		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.US);
		Date startDate = dateFormat.parse(billBean.getStartDate());
		Date endDate = dateFormat.parse(billBean.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date todayDate = calendar.getTime();

		// Check whether the start date is before the today date
		if (startDate.before(todayDate)) {

			String msg = "";
			if (billBean.isRecursive()) {
				msg = "The start date should not be before today date";
			}

			else {
				msg = "The date should not be before today date";
			}
			throw new BillException(msg);
		}

		// Check whether the end date is after the today date
		if (endDate.before(todayDate)) {
			String msg = "";
			if (billBean.isRecursive()) {
				msg = "The end date should not be before today date";
			}

			else {
				msg = "The date should not be before today date";
			}

			throw new BillException(msg);
		}

		// Check whether the end date is before the today date
		if (endDate.before(startDate)) {
			throw new BillException("The end date should be after the start date");
		}
	}

	/**
	 * This will start the target activity
	 * 
	 * @param context
	 * @param target
	 */
	public static void startNextActivity(Context context, Class<? extends Activity> target) {
		Intent targetIntent = new Intent(context, target);
		context.startActivity(targetIntent);
	}

	/**
	 * This will start the target activity with bundle
	 * 
	 * @param context
	 * @param target
	 */
	public static void startNextActivity(Context context, Class<? extends Activity> target, Bundle bundle) {
		Intent targetIntent = new Intent(context, target);
		targetIntent.putExtras(bundle);
		context.startActivity(targetIntent);
	}

	/**
	 * This will convert the integers to the hex color string
	 */
	public static String convertToHexString(int red, int green, int blue) {


		String redHex = Integer.toHexString(red);
		redHex = redHex.length() == 1 ? "0" + redHex : redHex;

		String greenHex = Integer.toHexString(green);
		greenHex = greenHex.length() == 1 ? "0" + greenHex : greenHex;

		String blueHex = Integer.toHexString(blue);
		blueHex = blueHex.length() == 1 ? "0" + blueHex : blueHex;

		String colorCode = "#" + redHex + greenHex + blueHex;

		return colorCode;
	}
}
