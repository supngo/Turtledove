package com.naturecode.turtledove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.naturecode.turtledove.R;
import com.naturecode.turtledove.database.DatabaseHandler;
import com.naturecode.turtledove.database.Guest;
import com.naturecode.turtledove.utils.GlobalVariables;

public class AddGuest extends SherlockActivity {

	private ImageButton add_button;
	private ImageButton back_button;
	private EditText firstname;
	private EditText lastname;
	private EditText table_no;
	private EditText no_guest;
	private TextView add_guest_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		int width = ((GlobalVariables) this.getApplication()).getScreen_width();
		if (width > 720){
			if(width > 736)				
				setContentView(R.layout.add_guest_galaxytab);
			else
				setContentView(R.layout.add_guest_nexus7);
		}
		else
			setContentView(R.layout.add_guest_galaxy);

		initControls();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, TableLookup.class);
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
		no_guest = (EditText) findViewById(R.id.no_guest_input);
		table_no = (EditText) findViewById(R.id.table_no_input);
		add_guest_confirm = (TextView) findViewById(R.id.add_guest_confirm);

		add_button = (ImageButton) findViewById(R.id.add_button);
		add_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String error = "";
				if (firstname.getText().toString() == null
						|| firstname.getText().toString().trim().length() < 1) {
					error = "First Name cannot be empty.";

				} else if (lastname.getText().toString() == null
						|| lastname.getText().toString().trim().length() < 1) {
					error = "Last Name cannot be empty.";
				} else if (no_guest.getText().toString() == null
						|| no_guest.getText().toString().trim().length() < 1) {
					error = "No of Guests cannot be empty.";
				} else if (table_no.getText().toString() == null
						|| table_no.getText().toString().trim().length() < 1) {
					error = "Table # cannot be empty.";
				}
				if (error != null && error.trim().length() > 0) {
					Toast toast = Toast.makeText(getApplicationContext(),
							error, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Guest guest = new Guest(firstname.getText().toString(),
							lastname.getText().toString(), no_guest.getText()
									.toString(), table_no.getText().toString());
					addGuest(guest);
				}
			}
		});

		back_button = (ImageButton) findViewById(R.id.back_button);
		back_button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// Intent myIntent = new Intent(getApplicationContext(),
				// TableLookup.class);
				// startActivity(myIntent);
				// finish();
				Intent intent = new Intent(getApplicationContext(),
						TableLookup.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void addGuest(Guest guest) {
		DatabaseHandler db = new DatabaseHandler(this);
		long result = db.addGuest(guest);
		if (result > 0) {
			firstname.setText("");
			lastname.setText("");
			no_guest.setText("");
			table_no.setText("");
			add_guest_confirm.setText(guest.getFname() + " " + guest.getLname()
					+ " has been added");
			add_guest_confirm.setVisibility(View.VISIBLE);
			firstname.requestFocus();
		}
	}
}