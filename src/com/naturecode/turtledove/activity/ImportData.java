package com.naturecode.turtledove.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.naturecode.turtledove.R;
import com.naturecode.turtledove.database.DatabaseHandler;
import com.naturecode.turtledove.utils.GlobalVariables;

public class ImportData extends SherlockActivity {

	private static final String appKey = "a7pg890m7au6wfy";
	private static final String appSecret = "01hmstf5u0yx0yz";

	private static final int REQUEST_LINK_TO_DBX = 0;
	private static final String TAG = "ImportData";

	private TextView input_result;
	private ImageButton home_button;
	private DbxAccountManager mDbxAcctMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		int width = ((GlobalVariables) this.getApplication()).getScreen_width();
		if (width > 720){
			if(width > 736)				
				setContentView(R.layout.importdata_galaxytab);
			else
				setContentView(R.layout.importdata_nexus7);
		}
		else
			setContentView(R.layout.importdata_galaxy);

		input_result = (TextView) findViewById(R.id.import_result);
		home_button = (ImageButton) findViewById(R.id.submit_button);
		home_button.setOnClickListener(new Button.OnClickListener() {
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

		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(),
				appKey, appSecret);
		if (mDbxAcctMgr.hasLinkedAccount()) {
			importFromDropbox();
		} else {
			mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
		}

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				importFromDropbox();
			} else {
				CharSequence error = "Link to Dropbox failed or was cancelled";
				Toast toast = Toast.makeText(getApplicationContext(), error,
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
				toast.show();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void importFromDropbox() {
		try {
			final String file_name = "Turtledove_data.txt";
			DbxPath file_path = new DbxPath(DbxPath.ROOT, file_name);
			DbxFile data_file;

			// Create DbxFileSystem for synchronized file access.
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr
					.getLinkedAccount());

			// Create a test file only if it doesn't already exist.
			DatabaseHandler db = new DatabaseHandler(this);
			String data_retrieved = "";

			if (!dbxFs.exists(file_path)) {
				data_file = dbxFs.create(file_path);
				input_result.setText("Data Not Found! Imported Failed!");
			} else {
				data_file = dbxFs.open(file_path);
				try {
					data_retrieved = data_file.readString();
					db.importData(data_retrieved);
					input_result.setText("Imported Data Successfully!");
				} finally {
					data_file.close();
				}
				Log.v(TAG, "\n\nDATA IMPORTED.\n\n");
			}
		} catch (IOException e) {
			input_result.setText("Imported Data Fail");
			CharSequence error = "Dropbox connection failed";
			Toast toast = Toast.makeText(getApplicationContext(), error,
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
}