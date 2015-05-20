package com.naturecode.turtledove.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.naturecode.turtledove.R;
import com.naturecode.turtledove.database.DatabaseHandler;
import com.naturecode.turtledove.database.Guest;
import com.naturecode.turtledove.utils.CustomAdapter;
import com.naturecode.turtledove.utils.GlobalVariables;
import com.naturecode.turtledove.utils.RowData;

public class ListAllGuests extends SherlockListActivity {
	private LayoutInflater mInflater;
	List<RowData> guestList = new ArrayList<RowData>();
	RowData rd;
	CustomAdapter adapter;
	// private static final String TAG = "TableResult";
	private Integer[] atrributes = { R.layout.guest_list, R.id.guest_name,
			R.id.guest_number, R.id.guest_icon };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.listview);
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		createResult("name");
	}

	private void createResult(String sort_type) {
		guestList = listGuest(sort_type);

		if (guestList != null && guestList.size() > 0) {
			((GlobalVariables) getApplication()).setSearch_result("all");
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

			getListView().setOnItemLongClickListener(
					new OnItemLongClickListener() {
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							selectOption(position);
							return true;
						}
					});
		} else {
			ListView listView = (ListView) findViewById(android.R.id.list);
			listView.setEmptyView(findViewById(android.R.id.empty));
			listView.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1,
					new ArrayList<String>()));
		}
	}

	private void selectOption(int position) {

		final int index = position;
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(
				ListAllGuests.this);
		helpBuilder.setTitle("Removing guest");
		helpBuilder.setPositiveButton("Remove",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						RowData selected_guest = guestList.get(index);
						Guest deleted_guest = new Guest();
						deleted_guest.setId(selected_guest.getId());
						deleted_guest.setFname(selected_guest.getName());
						deleteGuest(deleted_guest);
					}
				});
		helpBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		helpBuilder.show();
	}

	private void deleteGuest(Guest guest) {
		DatabaseHandler db = new DatabaseHandler(this);
		int i = 0;
		i = db.deleteContact(guest);
		createResult("name");
		if (i > 0) {
			Toast toast = Toast.makeText(getApplicationContext(),
					guest.getFname() + " has been deleted.", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	private List<RowData> listGuest(String sort_type) {
		List<RowData> result = new ArrayList<RowData>();
		DatabaseHandler db = new DatabaseHandler(this);
		if (sort_type != null && sort_type.equalsIgnoreCase("name"))
			result = db.getAllGuests_orderByName();
		else {
			result = db.getAllGuests_orderTable();
		}

		return result;
	}

	private void GuestDetail(int position) {
		RowData selected_guest = guestList.get(position);
		Intent intent = new Intent(getApplicationContext(), GuestDetail.class);
		intent.putExtra("fname", "" + selected_guest.getName());
		intent.putExtra("guest", "" + selected_guest.getNumber_of_guest());
		intent.putExtra("table", "" + selected_guest.getTable_number());
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listallguests, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, TableLookup.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.list_guest_byname:
			createResult("name");
			return true;
		case R.id.list_guest_bytable:
			createResult("table");
			return true;
		case R.id.deleteallguests:
			deleteAllGuests();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void deleteAllGuests() {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(
				ListAllGuests.this);
		helpBuilder.setTitle("Deleting all Guests?");
		helpBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						deleteAllGuests_DAO();
						createResult("name");
					}
				});
		helpBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		helpBuilder.show();
	}

	private void deleteAllGuests_DAO() {
		DatabaseHandler db = new DatabaseHandler(this);
		db.deleteAllGuests();
	}
}