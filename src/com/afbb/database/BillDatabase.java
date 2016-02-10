package com.afbb.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.afbb.adapter.RecurringBean;
import com.afbb.bean.BillBean;
import com.afbb.bean.Category;
import com.afbb.bean.ChildListBean;
import com.afbb.bean.DetailBean;
import com.afbb.bean.HomeListBean;
import com.afbb.bean.ParentListBean;
import com.afbb.utill.GlobalData;
import com.afbb.utill.Utility;

/**
 * This is the database class for the application
 */
public class BillDatabase extends SQLiteOpenHelper {

	private Context context;

	// Constants for category table
	public static final String CAT_TABLE = "categories";
	public static final String CAT_NAME = "category_name";
	public static final String CAT_COLOR = "category_color";

	private static final String tag = "BillDatabase";

	// Constants for bill table
	public static final String BILL_TABLE = "bill_table";
	public static final String BILL_ID = "bill_id";
	public static final String BILL_NAME = "bill_name";
	public static final String BILL_CATEGORY = "bill_category";
	public static final String BILL_TYPE = "bill_type";
	public static final String BILL_AMOUNT = "bill_amount";
	public static final String BILL_IS_RECURSIVE = "is_recursive";
	public static final String BILL_RECURSION_PERIOD = "recurssion_period";
	public static final String BILL_START_DATE = "bill_start_date";
	public static final String BILL_END_DATE = "bill_end_date";
	public static final String BILL_DESCRIPTION = "bill_description";

	// Constants for record table
	public static final String RECORD_TABLE = "record_table";
	public static final String RECORD_ID = "record_id";
	public static final String BILL_REF_ID = "bill_ref_id";
	public static final String RECORD_DATE = "record_date";
	public static final String RECORD_DATE_MILLIS = "record_date_millis";
	public static final String RECORD_IS_PAID = "record_is_paid";
	public static final String RECORD_PAID_DATE = "record_paid_date";

	public static final String RECORD_DAY = "record_day";
	public static final String RECORD_MONTH = "record_month";
	public static final String RECORD_YEAR = "record_year";
	public static final String RECORD_BILL_AMOUNT = "record_bill_amount";

	// public static final String BILL_IS_PAID = "bill_is_paid";
	// public static final String BILL_PAID_DATE = "bill_paid_date";

	/**
	 * The parameterized constructor
	 */
	public BillDatabase(Context context) {
		super(context, "bills.db", null, 4);
		this.context = context;
	}

	/**
	 * Create the required tables here
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String catTable = "create table " + CAT_TABLE + "(" + CAT_NAME + " text primary key," + CAT_COLOR + " text unique)";

		String billTable = "create table " + BILL_TABLE + "(" + BILL_ID + " integer primary key autoincrement," + BILL_NAME + " text," + BILL_CATEGORY
				+ " text," + BILL_TYPE + " text," + BILL_AMOUNT + " integer," + BILL_DESCRIPTION + " text, " + BILL_IS_RECURSIVE + " boolean,"
				+ BILL_RECURSION_PERIOD + " text," + BILL_START_DATE + " text," + BILL_END_DATE + ")";

		String recordTable = "create table " + RECORD_TABLE + "(" + RECORD_ID + " integer primary key autoincrement, " + BILL_REF_ID + " integer not null, "
				+ RECORD_DATE + " text, " + RECORD_DATE_MILLIS + " integer, " + RECORD_BILL_AMOUNT + " long, " + RECORD_IS_PAID + " boolean, "
				+ RECORD_PAID_DATE + " text, " + RECORD_DAY + " integer, " + RECORD_MONTH + " integer, " + RECORD_YEAR + " integer, FOREIGN KEY(" + BILL_REF_ID
				+ ") REFERENCES " + BILL_TABLE + "(" + BILL_ID + ") ON DELETE CASCADE )";

		db.execSQL(billTable);
		db.execSQL(recordTable);

		db.execSQL(catTable);

		// Insert default values here
		ContentValues contentValues = new ContentValues();

		contentValues.put(CAT_NAME, "Entertainment");
		contentValues.put(CAT_COLOR, "#008000");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Food and Drinks");
		contentValues.put(CAT_COLOR, "#0000FF");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Transportation");
		contentValues.put(CAT_COLOR, "#5F9EA0");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Health");
		contentValues.put(CAT_COLOR, "#7FFF00");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Donation");
		contentValues.put(CAT_COLOR, "#D2691E");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Gifts");
		contentValues.put(CAT_COLOR, "#2E8B57");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Grocery");
		contentValues.put(CAT_COLOR, "#00CED1");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Education");
		contentValues.put(CAT_COLOR, "#FF69B4");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Stationary");
		contentValues.put(CAT_COLOR, "#E6E6FA");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Furniture");
		contentValues.put(CAT_COLOR, "#800000");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Electronocs");
		contentValues.put(CAT_COLOR, "#708090");
		db.insert(CAT_TABLE, null, contentValues);

		contentValues.put(CAT_NAME, "Other");
		contentValues.put(CAT_COLOR, "#000000");
		db.insert(CAT_TABLE, null, contentValues);

		Log.i(tag, CAT_TABLE + " created");
	}

	/**
	 * If the database version is changed then delete the tables
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + CAT_TABLE);
		db.execSQL("drop table if exists " + BILL_TABLE);
		db.execSQL("drop table if exists " + RECORD_TABLE);
	}

	/**
	 * This method will one category to the categories table
	 * 
	 * @param category
	 */
	public void addCategory(String category, String color) throws SQLiteConstraintException {
		SQLiteDatabase database = getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(CAT_NAME, category);
		contentValues.put(CAT_COLOR, color);

		database.insertOrThrow(CAT_TABLE, null, contentValues);

		database.close();
	}

	/**
	 * This method will return the list of categories in the database
	 * 
	 * @return
	 */
	public ArrayList<String> getCategoryNamesList() {

		ArrayList<String> categoriesList = new ArrayList<String>();

		SQLiteDatabase database = getReadableDatabase();

		Cursor categoryCursor = database.query(CAT_TABLE, null, null, null, null, null, CAT_NAME);

		// Iterate the cursor and add the categories to the categories list
		while (categoryCursor.moveToNext()) {
			String category = categoryCursor.getString(0);
			categoriesList.add(category);
		}

		categoryCursor.close();
		database.close();

		return categoriesList;

	}

	/**
	 * This will add bills to the database
	 */
	public void addBill(BillBean billBean) {

		SQLiteDatabase database = getWritableDatabase();

		// Begin transaction here
		database.beginTransaction();
		try {

			// Insert one entry in bill table
			ContentValues valuesForBillTable = new ContentValues();
			valuesForBillTable.put(BILL_NAME, billBean.getName());
			valuesForBillTable.put(BILL_CATEGORY, billBean.getCategory());
			valuesForBillTable.put(BILL_TYPE, billBean.getType());
			valuesForBillTable.put(BILL_AMOUNT, billBean.getAmount());
			valuesForBillTable.put(BILL_DESCRIPTION, billBean.getDescription());
			valuesForBillTable.put(BILL_IS_RECURSIVE, billBean.isRecursive());
			valuesForBillTable.put(BILL_RECURSION_PERIOD, billBean.getRecursionPeriod());
			valuesForBillTable.put(BILL_START_DATE, billBean.getStartDate());
			valuesForBillTable.put(BILL_END_DATE, billBean.getEndDate());

			long id = database.insert(BILL_TABLE, null, valuesForBillTable);

			// Insert multiple records in record table
			ArrayList<String> datesList = Utility.getDatesList(billBean.getRecursionPeriod(), billBean.getStartDate(), billBean.getEndDate());

			for (String date : datesList) {
				ContentValues values = new ContentValues();
				values.put(BILL_REF_ID, id);
				values.put(RECORD_DATE, date);

				long timeMillis = Utility.convertDateToMillis(date);
				values.put(RECORD_DATE_MILLIS, timeMillis);
				values.put(RECORD_IS_PAID, false);
				values.put(RECORD_DAY, Utility.getDayFromMillis(timeMillis));
				values.put(RECORD_MONTH, Utility.getMonthFromMillis(timeMillis));
				values.put(RECORD_YEAR, Utility.getYearFromMillis(timeMillis));
				values.put(RECORD_BILL_AMOUNT, billBean.getAmount());

				database.insert(RECORD_TABLE, null, values);
			}

			database.setTransactionSuccessful();
		} catch (Exception e) {
			Toast.makeText(context, "Error in adding record", Toast.LENGTH_SHORT).show();
			Log.e(tag, "Error in adding record");
		}

		// End transaction here
		finally {
			database.endTransaction();
			database.close();
		}

	}

	/**
	 * This method will return the current month info
	 */
	public ArrayList<Integer> getBillDaysInMonth(int month, int year) {
		SQLiteDatabase database = getReadableDatabase();

		ArrayList<Integer> datesList = new ArrayList<Integer>();

		// Cursor cursor = database.rawQuery(query, null);
		Cursor cursor = database.query(true, RECORD_TABLE, new String[] { RECORD_DAY }, RECORD_MONTH + "  = ? and " + RECORD_YEAR + " = ?", new String[] {
				month + "", year + "" }, null, null, null, null);

		while (cursor.moveToNext()) {
			datesList.add(cursor.getInt(0));
		}

		cursor.close();

		database.close();

		return datesList;
	}

	/**
	 * This method will return the list of days which contains the unpaid bills in the month of year
	 */
	public ArrayList<Integer> getUnpaidBillsDaysInMonth(int month, int year) {

		ArrayList<Integer> unPaidDaysList = new ArrayList<Integer>();

		SQLiteDatabase database = getReadableDatabase();

		String qry = "SELECT " + RECORD_DAY + " from " + RECORD_TABLE + " where " + RECORD_IS_PAID + " = 0 and " + RECORD_MONTH + " = " + month + " and "
				+ RECORD_YEAR + " = " + year + " group by " + RECORD_DAY;

		// SELECT record_day from record_table where record_is_paid = 0 and record_month = 8 and record_year = 2014 group by record_is_paid

		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			int day = cursor.getInt(0);
			unPaidDaysList.add(day);
		}

		cursor.close();
		database.close();

		return unPaidDaysList;
	}

	public ArrayList<Category> getTheCategoryWiseData(long fromMillis, long toMillis, int type) {

		HashMap<String, String> colorMap = ((GlobalData) ((Activity) context).getApplication()).getColorMap();

		SQLiteDatabase database = getReadableDatabase();
		ArrayList<Category> categoryList = new ArrayList<Category>();

		String qry = "select " + BILL_CATEGORY + ", sum(" + RECORD_BILL_AMOUNT + ") from " + BILL_TABLE + ", " + RECORD_TABLE + " where (" + BILL_ID + " = "
				+ BILL_REF_ID + ") and (" + RECORD_DATE_MILLIS + " >= " + fromMillis + " and " + RECORD_DATE_MILLIS + " <= " + toMillis + ") and " + "("
				+ RECORD_IS_PAID + " != " + type + ")" + " group by " + BILL_CATEGORY;

		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			Category category = new Category();
			category.setCategoryName(cursor.getString(0));
			category.setCategoryAmount(cursor.getFloat(1));
			category.setCategoryColor(colorMap.get(cursor.getString(0)));

			categoryList.add(category);

		}

		database.close();
		return categoryList;
	}

	/**
	 * This method will return the bill of the provided date
	 */
	public ArrayList<HomeListBean> getBillsByDay(int currentDay, int currnetMonth, int currentYear) {

		SQLiteDatabase database = getReadableDatabase();
		ArrayList<HomeListBean> beanList = new ArrayList<HomeListBean>();

		String query = "select " + RECORD_ID + ", " + BILL_NAME + ", " + BILL_CATEGORY + ", " + BILL_AMOUNT + ", " + RECORD_DATE + ", " + RECORD_IS_PAID + ", "
				+ RECORD_PAID_DATE + ", " + BILL_DESCRIPTION + " from " + RECORD_TABLE + ", " + BILL_TABLE + " where " + BILL_REF_ID + " = " + BILL_ID
				+ " and ( " + RECORD_DAY + " = " + currentDay + " and " + RECORD_MONTH + " = " + currnetMonth + " and " + RECORD_YEAR + " = " + currentYear
				+ " )";
		Cursor cursor = database.rawQuery(query, null);

		// Iterate cursor
		while (cursor.moveToNext()) {
			long recordId = cursor.getLong(0);
			String billName = cursor.getString(1);
			String billCategory = cursor.getString(2);
			float billAmount = cursor.getFloat(3);
			String billDate = cursor.getString(4);
			int isPaid = cursor.getInt(5);
			String paidDate = cursor.getString(6);
			String description = cursor.getString(7);

			HomeListBean bean = new HomeListBean();
			bean.setRecordId(recordId);
			bean.setBillName(billName);
			bean.setCategory(billCategory);
			bean.setAmount(billAmount);
			bean.setDate(billDate);
			bean.setDescription(description);

			// Check whether the bill is paid or not
			if (isPaid == 0) {
				bean.setPaid(false);
			}

			else {
				bean.setPaid(true);
				bean.setPaidDate(paidDate);
			}

			beanList.add(bean);
		}

		cursor.close();

		database.close();

		return beanList;
	}

	/**
	 * This will update the unpaid record to paid record by id
	 * 
	 * @param recordId
	 */
	public void updateBillRecordById(long recordId) {

		// Get todays date in string format
		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT, Locale.US);
		Calendar calendar = Calendar.getInstance();
		String date = dateFormat.format(calendar.getTime());

		SQLiteDatabase database = getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(RECORD_IS_PAID, true);
		contentValues.put(RECORD_PAID_DATE, date);

		// Update the record
		database.update(RECORD_TABLE, contentValues, RECORD_ID + " =  ?", new String[] { recordId + "" });

		database.close();

	}

	/**
	 * This method will return current month over due bills, upcoming bills and paid bills
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public ArrayList<ParentListBean> getParentListData(int day, int month, int year) {
		ArrayList<ParentListBean> parentListBeans = new ArrayList<ParentListBean>();
		SQLiteDatabase database = getReadableDatabase();

		// Get the overdue bills
		String overDueQuery = "SELECT count(" + RECORD_ID + "), sum(" + RECORD_BILL_AMOUNT + "), " + BILL_TYPE + " from " + RECORD_TABLE + ", " + BILL_TABLE
				+ " where (" + BILL_ID + " = " + BILL_REF_ID + ") and (" + RECORD_DAY + " < " + day + " and " + RECORD_MONTH + " = " + month + " and "
				+ RECORD_YEAR + " = " + year + " and " + RECORD_IS_PAID + " = 0 ) group by " + BILL_TYPE + " order by " + BILL_TYPE + " asc";

		Cursor overDueCorsor = database.rawQuery(overDueQuery, null);
		// If any over due bills then iterate the cursor
		if (overDueCorsor.moveToFirst()) {
			ParentListBean bean = new ParentListBean();
			bean.setBillType("Overdue Bills");
			int numRecords = 0;
			do {
				String type = overDueCorsor.getString(2);
				numRecords = numRecords + overDueCorsor.getInt(0);

				// Get payable amount sum
				if (type.equals("payable")) {
					bean.setPayableAmount(overDueCorsor.getFloat(1));
				}

				// Get receivable amount sum
				else if (type.equals("receivable")) {
					bean.setReceivableAmount(overDueCorsor.getFloat(1));
				}

			} while (overDueCorsor.moveToNext());

			bean.setRecords(numRecords);
			parentListBeans.add(bean);
		}
		overDueCorsor.close();

		// Get the upcoming bills
		String upComingQuery = "SELECT count(" + RECORD_ID + "), sum(" + RECORD_BILL_AMOUNT + "), " + BILL_TYPE + " from " + RECORD_TABLE + ", " + BILL_TABLE
				+ " where (" + BILL_ID + " = " + BILL_REF_ID + ") and (" + RECORD_DAY + " >= " + day + " and " + RECORD_MONTH + " = " + month + " and "
				+ RECORD_YEAR + " = " + year + " and " + RECORD_IS_PAID + " = 0 ) group by " + BILL_TYPE + " order by " + BILL_TYPE + " asc";

		Cursor upComingCursor = database.rawQuery(upComingQuery, null);
		// If any Upcoming bills then iterate the cursor
		if (upComingCursor.moveToFirst()) {
			ParentListBean bean = new ParentListBean();
			bean.setBillType("Upcoming Bills");
			int numRecords = 0;
			do {
				String type = upComingCursor.getString(2);
				numRecords = numRecords + upComingCursor.getInt(0);

				// Get payable amount sum
				if (type.equals("payable")) {
					bean.setPayableAmount(upComingCursor.getFloat(1));
				}

				// Get receivable amount sum
				else if (type.equals("receivable")) {
					bean.setReceivableAmount(upComingCursor.getFloat(1));
				}

			} while (upComingCursor.moveToNext());

			bean.setRecords(numRecords);
			parentListBeans.add(bean);
		}
		upComingCursor.close();

		// Get paid bills
		// Get the upcoming bills
		String paidQuery = "SELECT count(" + RECORD_ID + "), sum(" + RECORD_BILL_AMOUNT + "), " + BILL_TYPE + " from " + RECORD_TABLE + ", " + BILL_TABLE
				+ " where (" + BILL_ID + " = " + BILL_REF_ID + ") and (" + RECORD_MONTH + " = " + month + " and " + RECORD_YEAR + " = " + year + " and "
				+ RECORD_IS_PAID + " = 1 ) group by " + BILL_TYPE + " order by " + BILL_TYPE + " asc";

		Cursor paidCursor = database.rawQuery(paidQuery, null);
		// If any paid bills then iterate the cursor
		if (paidCursor.moveToFirst()) {
			ParentListBean bean = new ParentListBean();
			bean.setBillType("Paid Bills");
			int numRecords = 0;
			do {
				String type = paidCursor.getString(2);
				numRecords = numRecords + paidCursor.getInt(0);

				// Get payable amount sum
				if (type.equals("payable")) {
					bean.setPayableAmount(paidCursor.getFloat(1));
				}

				// Get receivable amount sum
				else if (type.equals("receivable")) {
					bean.setReceivableAmount(paidCursor.getFloat(1));
				}

			} while (paidCursor.moveToNext());

			bean.setRecords(numRecords);
			parentListBeans.add(bean);

		}
		paidCursor.close();

		database.close();
		return parentListBeans;
	}

	/**
	 * This method will return the list of over due bills in month
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public ArrayList<ChildListBean> getOverDueBillsInMonth(int day, int month, int year) {

		ArrayList<ChildListBean> childListBeans = new ArrayList<ChildListBean>();
		SQLiteDatabase database = getReadableDatabase();

		String qry = "select " + RECORD_ID + ", " + BILL_NAME + ", " + RECORD_BILL_AMOUNT + ", " + BILL_DESCRIPTION + ", " + BILL_CATEGORY + ", " + RECORD_DAY
				+ " from " + BILL_TABLE + ", " + RECORD_TABLE + " where ( " + BILL_ID + " = " + BILL_REF_ID + " ) and ( " + RECORD_DAY + " < " + day + " and "
				+ RECORD_MONTH + " = " + month + " and " + RECORD_YEAR + " = " + year + " ) and " + RECORD_IS_PAID + " = 0 order by " + RECORD_DAY;
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			ChildListBean bean = new ChildListBean();
			long id = cursor.getLong(0);
			String name = cursor.getString(1);
			float amount = cursor.getFloat(2);
			String desc = cursor.getString(3);
			String category = cursor.getString(4);
			int dayInt = cursor.getInt(5);

			bean.setId(id);
			bean.setName(name);
			bean.setDesc(desc);
			bean.setAmount(amount);
			bean.setPaid(false);
			bean.setCategory(category);
			bean.setDay(dayInt);
			bean.setWeek(Utility.getWeekShortName(dayInt, month, year));

			childListBeans.add(bean);
		}

		cursor.close();

		database.close();

		return childListBeans;
	}

	/**
	 * This method will return the list of upcoming due bills in month
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public ArrayList<ChildListBean> getUpCommingBills(int day, int month, int year) {

		ArrayList<ChildListBean> childListBeans = new ArrayList<ChildListBean>();
		SQLiteDatabase database = getReadableDatabase();

		String qry = "select " + RECORD_ID + ", " + BILL_NAME + ", " + RECORD_BILL_AMOUNT + ", " + BILL_DESCRIPTION + ", " + BILL_CATEGORY + ", " + RECORD_DAY
				+ " from " + BILL_TABLE + ", " + RECORD_TABLE + " where ( " + BILL_ID + " = " + BILL_REF_ID + " ) and ( " + RECORD_DAY + " >= " + day + " and "
				+ RECORD_MONTH + " = " + month + " and " + RECORD_YEAR + " = " + year + " ) and " + RECORD_IS_PAID + " = 0 order by " + RECORD_DAY;
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			ChildListBean bean = new ChildListBean();
			long id = cursor.getLong(0);
			String name = cursor.getString(1);
			float amount = cursor.getFloat(2);
			String desc = cursor.getString(3);
			String category = cursor.getString(4);
			int dayInt = cursor.getInt(5);

			bean.setId(id);
			bean.setName(name);
			bean.setDesc(desc);
			bean.setAmount(amount);
			bean.setPaid(false);
			bean.setCategory(category);
			bean.setDay(dayInt);
			bean.setWeek(Utility.getWeekShortName(dayInt, month, year));

			childListBeans.add(bean);
		}

		cursor.close();

		database.close();

		return childListBeans;
	}

	/**
	 * This will return the paid bill in the month
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public ArrayList<ChildListBean> getPaidBills(int month, int year) {

		ArrayList<ChildListBean> childListBeans = new ArrayList<ChildListBean>();
		SQLiteDatabase database = getReadableDatabase();

		String qry = "select " + RECORD_ID + ", " + BILL_NAME + ", " + RECORD_BILL_AMOUNT + ", " + BILL_DESCRIPTION + ", " + BILL_CATEGORY + ", " + RECORD_DAY
				+ ", " + RECORD_PAID_DATE + " from " + BILL_TABLE + ", " + RECORD_TABLE + " where ( " + BILL_ID + " = " + BILL_REF_ID + " ) and ( "
				+ RECORD_MONTH + " = " + month + " and " + RECORD_YEAR + " = " + year + " ) and " + RECORD_IS_PAID + " = 1 order by " + RECORD_DAY;
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			ChildListBean bean = new ChildListBean();
			long id = cursor.getLong(0);
			String name = cursor.getString(1);
			float amount = cursor.getFloat(2);
			String desc = cursor.getString(3);
			String category = cursor.getString(4);
			int dayInt = cursor.getInt(5);
			String paidDate = cursor.getString(6);

			bean.setId(id);
			bean.setName(name);
			bean.setDesc(desc);
			bean.setAmount(amount);
			bean.setPaid(true);
			bean.setCategory(category);
			bean.setDay(dayInt);
			bean.setWeek(Utility.getWeekShortName(dayInt, month, year));
			bean.setPaidDate(paidDate);

			childListBeans.add(bean);
		}

		cursor.close();

		database.close();

		return childListBeans;
	}

	/**
	 * This method will return the mapping of category and colr
	 * 
	 * @return
	 */
	public HashMap<String, String> getColorMap() {

		HashMap<String, String> colorMap = new HashMap<String, String>();
		SQLiteDatabase database = getReadableDatabase();

		Cursor cursor = database.query(CAT_TABLE, null, null, null, null, null, null);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			colorMap.put(cursor.getString(0), cursor.getString(1));
		}

		database.close();
		return colorMap;
	}

	/**
	 * This method will returns the list of recurring bills
	 * 
	 * @return
	 */
	public ArrayList<RecurringBean> getRecurringBills() {

		ArrayList<RecurringBean> recurringList = new ArrayList<RecurringBean>();

		SQLiteDatabase database = getReadableDatabase();

		// Get the cursor
		Cursor cursor = database.query(BILL_TABLE, new String[] { BILL_ID, BILL_NAME, BILL_CATEGORY, BILL_AMOUNT, BILL_DESCRIPTION, BILL_RECURSION_PERIOD,
				BILL_START_DATE, BILL_END_DATE }, BILL_IS_RECURSIVE + " = ?", new String[] { "1" }, null, null, null);

		// Iterate cursor
		while (cursor.moveToNext()) {
			long billId = cursor.getLong(0);
			String billName = cursor.getString(1);
			String billCategory = cursor.getString(2);
			float amount = cursor.getFloat(3);
			String desc = cursor.getString(4);
			String recursionPeriod = cursor.getString(5);
			String startDate = cursor.getString(6);
			String endDate = cursor.getString(7);

			// Create the bean object and set the data
			RecurringBean bean = new RecurringBean();
			bean.setBillId(billId);
			bean.setName(billName);
			bean.setCategory(billCategory);
			bean.setAmount(amount);
			bean.setDescription(desc);

			bean.setStartDate(startDate);
			bean.setEndDate(endDate);

			bean.setStartWeek(Utility.getWeekNameFromDateString(startDate));
			bean.setEndWeek(Utility.getWeekNameFromDateString(endDate));

			bean.setRecursionPeriod(recursionPeriod);

			// Add to array list
			recurringList.add(bean);

		}

		cursor.close();
		database.close();
		return recurringList;
	}

	/**
	 * This will return the payable sum
	 * 
	 * @return
	 */
	public float getPayableSum() {
		SQLiteDatabase database = getReadableDatabase();

		float payableSum = 0;

		String qry = "select sum(" + RECORD_BILL_AMOUNT + ") from " + RECORD_TABLE + ", " + BILL_TABLE + " where " + BILL_ID + "=" + BILL_REF_ID + " and "
				+ BILL_TYPE + " like 'payable'";
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		if (cursor.moveToFirst()) {
			payableSum = cursor.getFloat(0);
		}

		cursor.close();
		database.close();
		return payableSum;

	}

	/**
	 * This will return the receivable sum
	 * 
	 * @return
	 */
	public float getReceivableSum() {
		SQLiteDatabase database = getReadableDatabase();

		float receivableSum = 0;

		String qry = "select sum(" + RECORD_BILL_AMOUNT + ") from " + RECORD_TABLE + ", " + BILL_TABLE + " where " + BILL_ID + "=" + BILL_REF_ID + " and "
				+ BILL_TYPE + " like 'receivable'";
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		if (cursor.moveToFirst()) {
			receivableSum = cursor.getFloat(0);
		}

		cursor.close();
		database.close();
		return receivableSum;

	}

	/**
	 * This will return the bill details based on bill id
	 * 
	 * @param recordId
	 * @return
	 */
	public DetailBean getBillById(long recordId) {
		SQLiteDatabase database = getReadableDatabase();
		DetailBean bean = new DetailBean();

		String qry = "SELECT " + BILL_NAME + ", " + BILL_TYPE + ", " + BILL_CATEGORY + ", " + BILL_AMOUNT + ", " + BILL_DESCRIPTION + ", " + BILL_IS_RECURSIVE
				+ ", " + BILL_START_DATE + ", " + BILL_END_DATE + "," + BILL_RECURSION_PERIOD + " from " + BILL_TABLE + ", " + RECORD_TABLE + " where "
				+ BILL_ID + " = " + BILL_REF_ID + " and " + RECORD_ID + " = " + recordId;
		Cursor cursor = database.rawQuery(qry, null);

		// Iterate the cursor
		if (cursor.moveToFirst()) {
			bean.setName(cursor.getString(0));
			bean.setType(cursor.getString(1));
			bean.setCategory(cursor.getString(2));
			bean.setAmount(cursor.getFloat(3));
			bean.setDescription(cursor.getString(4));
			int recursion = cursor.getInt(5);
			if (recursion == 1) {
				bean.setRecursion(true);
			} else {
				bean.setRecursion(false);
			}
			bean.setStartDate(cursor.getString(6));
			bean.setEndDate(cursor.getString(7));
			bean.setPeriod(cursor.getString(8));
		}

		cursor.close();
		database.close();
		return bean;
	}

	/**
	 * This method will delete the recursion bills
	 * 
	 * @param deleteItemId
	 *            the id of the bill
	 * @param deletionType
	 *            The type of the deletion. If 0 delete all the recursion items and its entry in the bill table. If 1 delete only unpaid bills of this
	 *            recursion
	 */
	public void deletedRecursion(long deleteItemId, int deletionType) {
		SQLiteDatabase database = getWritableDatabase();

		// Do the delete operation in transaction
		database.beginTransaction();
		try {

			/* If the deletion type is 0 then delete recursion entry from bill table and delete its children from record table */
			if (deletionType == 0) {
				database.delete(RECORD_TABLE, BILL_REF_ID + " = ?", new String[] { deleteItemId + "" });
				database.delete(BILL_TABLE, BILL_ID + " = ?", new String[] { deleteItemId + "" });
			}

			/* If the deletion type is 1 then delete the only unpaid records from the record table of type recursion */
			else {
				database.delete(RECORD_TABLE, BILL_REF_ID + " = ? and " + RECORD_IS_PAID + " = ?", new String[] { deleteItemId + "", "0" });
			}

			database.setTransactionSuccessful();
		} catch (Exception e) {
		}

		finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * This will delete the record from the record table for the id
	 * 
	 * @param id
	 *            Id of the record to be deleted
	 */
	public void deleteRecordByRecordId(long id) {
		SQLiteDatabase database = getWritableDatabase();

		database.delete(RECORD_TABLE, RECORD_ID + " = ?", new String[] { id + "" });

		database.close();
	}

	/**
	 * This will return the categories list with its color
	 * 
	 * @return
	 */
	public ArrayList<Category> getCategoriesList() {

		ArrayList<Category> categoriesList = new ArrayList<Category>();

		SQLiteDatabase database = getReadableDatabase();

		Cursor cursor = database.query(CAT_TABLE, null, null, null, null, null, CAT_NAME);

		// Iterate the cursor
		while (cursor.moveToNext()) {
			Category category = new Category();
			category.setCategoryName(cursor.getString(0));
			category.setCategoryColor(cursor.getString(1));

			categoriesList.add(category);
		}

		cursor.close();
		database.close();
		return categoriesList;
	}

	/**
	 * This will delete one category form the category table
	 * 
	 * @param categoryName
	 */
	public void deleteCategory(String categoryName) {
		SQLiteDatabase database = getWritableDatabase();

		database.delete(CAT_TABLE, CAT_NAME + " = ?", new String[] { categoryName });


		database.close();
	}
}
