package com.naturecode.turtledove.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naturecode.turtledove.utils.RowData;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "GuestAdmin";

	// Guest table name
	private static final String TABLE_Guest = "Guest";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_FNAME = "fname";
	private static final String KEY_LNAME = "lname";
	private static final String KEY_GUEST_NO = "number_of_guest";
	private static final String KEY_TABLE_NO = "table_number";

	private static final String TAG = "DatabaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Guest + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_FNAME + " TEXT,"
				+ KEY_LNAME + " TEXT," + KEY_GUEST_NO + " TEXT," + KEY_TABLE_NO
				+ " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Guest);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void cleanTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Guest);
		onCreate(db);
	}

	// Adding new guest
	public long addGuest(Guest guest) {
		long result = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		try {
			values.put(KEY_FNAME, guest.getFname());
			values.put(KEY_LNAME, guest.getLname());
			values.put(KEY_GUEST_NO, guest.getNumber_of_guest());
			values.put(KEY_TABLE_NO, guest.getTable_number());
			result = db.insert(TABLE_Guest, null, values);
		} finally {
			if (db != null)
				db.close();// Closing database connection
		}
		return result;
	}

	// Getting single/multiple guest by name
	public List<RowData> getGuest(String fname, String lname) {
		List<RowData> guestList = new ArrayList<RowData>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;

		try {
			if (fname != null && fname.trim().length() > 0 && lname != null
					&& lname.trim().length() > 0)
				cursor = db.query(TABLE_Guest, new String[] { KEY_ID,
						KEY_FNAME, KEY_LNAME, KEY_GUEST_NO, KEY_TABLE_NO },
						KEY_FNAME + " LIKE '" + fname + "%' AND " + KEY_LNAME
								+ " LIKE '" + lname + "%'", null, null, null,
						null);
			else if (fname != null && fname.trim().length() > 0)
				cursor = db.query(TABLE_Guest, new String[] { KEY_ID,
						KEY_FNAME, KEY_LNAME, KEY_GUEST_NO, KEY_TABLE_NO },
						KEY_FNAME + " LIKE '" + fname + "%'", null, null, null,
						null);
			else if (lname != null && lname.trim().length() > 0)
				cursor = db.query(TABLE_Guest, new String[] { KEY_ID,
						KEY_FNAME, KEY_LNAME, KEY_GUEST_NO, KEY_TABLE_NO },
						KEY_LNAME + " LIKE '" + lname + "%'", null, null, null,
						null);

			if (cursor != null && cursor.moveToFirst()) {
				int count = 0;
				do {
					RowData guest = new RowData();
					guest.setImage(count);
					guest.setName(cursor.getString(1) + " "
							+ cursor.getString(2));
					guest.setNumber_of_guest(cursor.getString(3));
					guest.setTable_number(cursor.getString(4));

					guestList.add(guest);
					count++;
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return guestList;
	}

	// Getting All Contacts order by Name
	public List<RowData> getAllGuests_orderByName() {
		List<RowData> guestList = new ArrayList<RowData>();
		String selectQuery = "SELECT  * FROM " + TABLE_Guest + " ORDER BY "
				+ KEY_FNAME + ", " + KEY_LNAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		try {
			if (cursor != null && cursor.moveToFirst()) {
				int count = 0;
				do {
					RowData guest = new RowData();
					guest.setImage(count);
					guest.setId(cursor.getInt(0));
					guest.setName(cursor.getString(1) + " "
							+ cursor.getString(2));
					guest.setNumber_of_guest(cursor.getString(3));
					guest.setTable_number(cursor.getString(4));

					guestList.add(guest);
					count++;
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return guestList;
	}

	// Getting All Contacts order by Table
	public List<RowData> getAllGuests_orderTable() {
		List<RowData> guestList = new ArrayList<RowData>();
		String selectQuery = "SELECT  * FROM " + TABLE_Guest + " ORDER BY "
				+ KEY_TABLE_NO + ", " + KEY_FNAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		try {
			// looping through all rows and adding to list
			if (cursor != null && cursor.moveToFirst()) {
				int count = 0;
				do {
					RowData guest = new RowData();
					guest.setImage(count);
					guest.setId(cursor.getInt(0));
					guest.setName(cursor.getString(1) + " "
							+ cursor.getString(2));
					guest.setNumber_of_guest(cursor.getString(3));
					guest.setTable_number(cursor.getString(4));

					guestList.add(guest);
					count++;
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return guestList;
	}

	// Exporting all guests into Dropbox
	public String getAllGuests_to_String() {
		String result = "";
		String selectQuery = "SELECT  * FROM " + TABLE_Guest + " ORDER BY "
				+ KEY_FNAME + ", " + KEY_LNAME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		try {
			// looping through all rows and adding to list
			if (cursor != null && cursor.moveToFirst()) {
				do {
					result += cursor.getString(1) + "," + cursor.getString(2)
							+ "," + cursor.getString(3) + ","
							+ cursor.getString(4) + ";\n";
				} while (cursor.moveToNext());
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return result;
	}

//	Importing all guests from Dropbox
	public void importData(String data) {
		cleanTable();
		SQLiteDatabase db = this.getWritableDatabase();
		String[] records = data.trim().split(";");
		try {
			for (String record : records) {
				String[] properties = record.split(",");
				ContentValues values = new ContentValues();
//				Log.v(TAG, "property 1: "+properties[0].trim());
//				Log.v(TAG, "property 2: "+properties[1].trim());
//				Log.v(TAG, "property 3: "+properties[2].trim());
//				Log.v(TAG, "property 4: "+properties[3].trim()+"\n");
				values.put(KEY_FNAME, properties[0].trim());
				values.put(KEY_LNAME, properties[1].trim());
				values.put(KEY_GUEST_NO, properties[2].trim());
				values.put(KEY_TABLE_NO, properties[3].trim());
				
				// Inserting Row
				db.insert(TABLE_Guest, null, values);
			}
		} finally {
			if (db != null)
				db.close(); // Closing database connection
		}
	}

	// Deleting single guest
	public int deleteContact(Guest guest) {
		SQLiteDatabase db = this.getWritableDatabase();
		int result = 0;
		try {
			result = db.delete(TABLE_Guest, KEY_ID + " = ?",
					new String[] { String.valueOf(guest.getId()) });
		} finally {
			if (db != null)
				db.close();
		}
		return result;
	}

	// Deleting all guests
	public void deleteAllGuests() {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.delete(TABLE_Guest, null, null);
		} finally {
			if (db != null)
				db.close();
		}
	}

	// Getting single guest by id
	// public Guest getGuest(int id) {
	// SQLiteDatabase db = this.getReadableDatabase();
	//
	// Cursor cursor = db.query(TABLE_Guest, new String[] { KEY_ID, KEY_FNAME,
	// KEY_LNAME, KEY_GUEST_NO, KEY_TABLE_NO }, KEY_ID + "=?",
	// new String[] { String.valueOf(id) }, null, null, null, null);
	// if (cursor != null)
	// cursor.moveToFirst();
	//
	// Guest new_guest = new Guest(Integer.parseInt(cursor.getString(0)),
	// cursor.getString(1), cursor.getString(2), cursor.getString(3),
	// cursor.getString(4));
	// return new_guest;
	// }

	// Updating single guest
	// public int updateGuest(Guest guest) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(KEY_FNAME, guest.getFname());
	// values.put(KEY_LNAME, guest.getFname());
	// values.put(KEY_GUEST_NO, guest.getNumber_of_guest());
	// values.put(KEY_TABLE_NO, guest.getTable_number());
	//
	// int result = 0;
	// result = db.update(TABLE_Guest, values, KEY_ID + " = ?", new String[] {
	// String.valueOf(guest.getId()) });
	// db.close();
	//
	// return result;
	// }

	// Getting guests Count
	// public int getContactsCount() {
	// String countQuery = "SELECT  * FROM " + TABLE_Guest;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(countQuery, null);
	// cursor.close();
	//
	// return cursor.getCount();
	// }
}