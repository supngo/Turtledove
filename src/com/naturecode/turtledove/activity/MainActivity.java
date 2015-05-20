package com.naturecode.turtledove.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.naturecode.turtledove.R;
import com.naturecode.turtledove.database.DatabaseHandler;

public class MainActivity extends Activity {

	private ImageButton start_button;

	// private static final String TAG = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_galaxy);
		DatabaseHandler db = new DatabaseHandler(this);
		db.cleanTable();

		initControls();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initControls() {
		start_button = (ImageButton) findViewById(R.id.main_start);
		start_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this,
						TableLookup.class);
				startActivity(myIntent);
			}
		});
	}
}