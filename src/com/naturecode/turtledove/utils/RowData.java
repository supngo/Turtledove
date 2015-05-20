package com.naturecode.turtledove.utils;

public class RowData {
	private int image;
	private int id;
	private String name;
	private String number_of_guest;
	private String table_number;

	public RowData() {
	};

	public RowData(int id, String fullname, String number_guest) {
		image = id;
		name = fullname;
		number_of_guest = number_guest;
	}

	public RowData(int id, int file_id, String fullname, String number_guest,
			String table) {
		this.id = id;
		name = fullname;
		number_of_guest = number_guest;
		table_number = table;
	}

	@Override
	public String toString() {
		return getImage() + " " + getName() + " " + getNumber_of_guest() + " "
				+ getTable_number();
	}

	public int getImage() {
		return image;
	}

	public void setImage(int mId) {
		this.image = mId;
	}

	public String getName() {
		return name;
	}

	public void setName(String mTitle) {
		this.name = mTitle;
	}

	public String getNumber_of_guest() {
		return number_of_guest;
	}

	public void setNumber_of_guest(String number_of_guest) {
		this.number_of_guest = number_of_guest;
	}

	public String getTable_number() {
		return table_number;
	}

	public void setTable_number(String table_number) {
		this.table_number = table_number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}