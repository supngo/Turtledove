package com.naturecode.turtledove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.naturecode.turtledove.R;
import com.naturecode.turtledove.utils.GlobalVariables;

public class GuestDetail extends SherlockActivity {
	private TextView guest_detail_1;
	private TextView guest_detail_2;
	private TextView guest_detail_3;
	private TextView guest_detail_4;
	private ImageButton home_button;
	private ImageButton back_button;
	private static final String TAG = "GuestDetail";
	private String result_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		int width = ((GlobalVariables) this.getApplication()).getScreen_width();
		if (width > 720){
			if(width > 736)				
				setContentView(R.layout.guest_detail_galaxytab);
			else
				setContentView(R.layout.guest_detail_nexus7);
		}
		else
			setContentView(R.layout.guest_detail_galaxy);
		initControls();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			result_type = ((GlobalVariables) this.getApplication())
					.getSearch_result();
			Intent intent;
			if (result_type != null && result_type.equalsIgnoreCase("list"))
				intent = new Intent(this, TableResult.class);
			else if (result_type != null && result_type.equalsIgnoreCase("all"))
				intent = new Intent(this, ListAllGuests.class);
			else
				intent = new Intent(this, TableLookup.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initControls() {
		// Log.v(TAG, "got initControls");
		guest_detail_1 = (TextView) findViewById(R.id.guest_detail_1);
		guest_detail_2 = (TextView) findViewById(R.id.guest_detail_2);
		guest_detail_3 = (TextView) findViewById(R.id.guest_detail_3);
		guest_detail_4 = (TextView) findViewById(R.id.guest_detail_4);

		Bundle extras = getIntent().getExtras();
		String name = "", table = "";
		if (extras != null) {
			// Log.v(TAG, "extras");
			name = extras.getString("fname");
			// guest = extras.getString("guest");
			table = extras.getString("table");
		}
		guest_detail_1.setText(name.toUpperCase());
		guest_detail_2.setText("Thanks for coming");
		guest_detail_3.setText("You will be seated at");
		guest_detail_4.setText("Table #" + table);

		home_button = (ImageButton) findViewById(R.id.home);
		home_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// Intent myIntent = new Intent(getApplicationContext(),
				// TableLookup.class);
				// startActivity(myIntent);
				// finish();

				Intent intent;
				intent = new Intent(getApplicationContext(), TableLookup.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		back_button = (ImageButton) findViewById(R.id.back);
		back_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				result_type = ((GlobalVariables) getApplicationContext())
						.getSearch_result();
				Intent intent;
				if (result_type != null && result_type.equalsIgnoreCase("list"))
					intent = new Intent(getApplicationContext(),
							TableResult.class);
				else if (result_type != null
						&& result_type.equalsIgnoreCase("all"))
					intent = new Intent(getApplicationContext(),
							ListAllGuests.class);
				else
					intent = new Intent(getApplicationContext(),
							TableLookup.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
}