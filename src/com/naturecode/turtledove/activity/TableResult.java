package com.naturecode.turtledove.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.naturecode.turtledove.R;
import com.naturecode.turtledove.database.DatabaseHandler;
import com.naturecode.turtledove.utils.CustomAdapter;
import com.naturecode.turtledove.utils.GlobalVariables;
import com.naturecode.turtledove.utils.RowData;

public class TableResult extends SherlockListActivity {
	private LayoutInflater mInflater;
	List<RowData> guestList = new ArrayList<RowData>();
	RowData rd;
	CustomAdapter adapter;
	private static final String TAG = "TableResult";
	private Integer[] atrributes = { R.layout.guest_list, R.id.guest_name,
			R.id.guest_number, R.id.guest_icon };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.listview);
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		createResult();
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

	private void createResult() {
		guestList = findGuest();

		if (guestList != null && guestList.size() > 1) {
			((GlobalVariables) getApplication()).setSearch_result("list");
			Integer[] images = new Integer[guestList.size()];
			for (int i = 0; i < guestList.size(); i++) {
				images[i] = R.drawable.guest_icon;
			}
			adapter = new CustomAdapter(this, guestList, mInflater, atrributes,
					images);
			setListAdapter(adapter);
			getListView().setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					GuestDetail(position);
				}
			});
		} else if (guestList.size() == 1) {
			((GlobalVariables) getApplication()).setSearch_result("single");
			GuestDetail(0);
		} else {
			ListView listView = (ListView) findViewById(android.R.id.list);
			listView.setEmptyView(findViewById(android.R.id.empty));
			listView.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1,
					new ArrayList<String>()));
		}
	}

	private List<RowData> findGuest() {
		List<RowData> result = new ArrayList<RowData>();
		String fname = "", lname = "";

		fname = ((GlobalVariables) this.getApplication()).getFname();
		lname = ((GlobalVariables) this.getApplication()).getLname();

		// Bundle extras = getIntent().getExtras();
		// if (extras != null) {
		// fname = extras.getString("firstname");
		// lname = extras.getString("lastname");
		// }

		DatabaseHandler db = new DatabaseHandler(this);
		result = db.getGuest(fname, lname);

		return result;
	}

	private void GuestDetail(int position) {
		// Log.v(TAG, "got GuestDetail");
		RowData selected_guest = guestList.get(position);
		Intent intent = new Intent(TableResult.this, GuestDetail.class);
		intent.putExtra("fname", "" + selected_guest.getName());
		intent.putExtra("guest", "" + selected_guest.getNumber_of_guest());
		intent.putExtra("table", "" + selected_guest.getTable_number());
		startActivity(intent);
		// finish();
	}

	// @Override
	// public void onBackPressed() {
	// super.onBackPressed();
	// }
}