package com.naturecode.turtledove.database;

public class Guest {
	int id;
	String fname;
	String lname;
	String number_of_guest;
	String table_number;

	public Guest() {

	}

	public Guest(int id, String fname, String lname, String number_guest,
			String table_number) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.number_of_guest = number_guest;
		this.table_number = table_number;
	}

	public Guest(String fname, String lname, String number_guest,
			String table_number) {
		this.fname = fname;
		this.lname = lname;
		this.number_of_guest = number_guest;
		this.table_number = table_number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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
}