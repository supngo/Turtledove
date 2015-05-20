package com.naturecode.turtledove.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.naturecode.turtledove.R;
import com.naturecode.turtledove.utils.GlobalVariables;

public class TableLookup extends Activity {

	private ImageButton submit_button;
	private EditText firstname;
	private EditText lastname;
	private int height = 720;
	private int width=0;
	private static final String TAG = "TableLookup";

	@Override
	@TargetApi(13)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= 13) {
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			height = size.y;
			width = size.x;
		}
		((GlobalVariables) getApplication()).setScreen_width(height);
		Log.w(TAG, "height: "+height);
		Log.w(TAG, "width: "+width);
		if (height > 720){
			if(height > 736)				
				setContentView(R.layout.tablelookup_galaxytab);
			else
				setContentView(R.layout.tablelookup_nexus7);
		}
		else {
			setContentView(R.layout.tablelookup_galaxy);
		}
		initControls();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tablelookup, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_guest:
			Intent intent = new Intent(this, AddGuest.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.allguests:
			intent = new Intent(this, ListAllGuests.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.importdata:
			intent = new Intent(this, ImportData.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.exportdata:
			intent = new Intent(this, ExportData.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initControls() {
		firstname = (EditText) findViewById(R.id.firstname_input);
		lastname = (EditText) findViewById(R.id.lastname_input);

		submit_button = (ImageButton) findViewById(R.id.submit_button);
		submit_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if ((firstname.getText().toString() == null || firstname
						.getText().toString().trim().length() < 1)
						&& (lastname.getText().toString() == null || lastname
								.getText().toString().trim().length() < 1)) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Please enter First or Last Name",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Intent myIntent = new Intent(getApplicationContext(),
							TableResult.class);
					// myIntent.putExtra("firstname",
					// firstname.getText().toString());
					// myIntent.putExtra("lastname",
					// lastname.getText().toString());

					((GlobalVariables) getApplication()).setFname(firstname
							.getText().toString());
					((GlobalVariables) getApplication()).setLname(lastname
							.getText().toString());
					startActivity(myIntent);

				}
			}
		});
	}
}