package com.naturecode.turtledove.utils;

import android.app.Application;

public class GlobalVariables extends Application {
	private int screen_width;
	private int screen_height;
	private String fname;
	private String lname;
	private String search_result;

	@Override
	public void onCreate() {
		// reinitialize variable
	}

	public int getScreen_width() {
		return screen_width;
	}

	public void setScreen_width(int screen_width) {
		this.screen_width = screen_width;
	}

	public int getScreen_height() {
		return screen_height;
	}

	public void setScreen_height(int screen_height) {
		this.screen_height = screen_height;
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

	public String getSearch_result() {
		return search_result;
	}

	public void setSearch_result(String search_result) {
		this.search_result = search_result;
	}
}