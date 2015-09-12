package com.example.dexter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ListICE extends Activity {

	
	ContactsDB db = new ContactsDB(getApplicationContext());
	
	int count = db.getContactsCount();
	
	List<ICE_Contact> contacts ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_ice);

		ListView lv= (ListView) findViewById(R.id.ices);
		
		contacts = db.getAllContacts();

		final MyCustomAdapter adapter = new MyCustomAdapter(); 

		lv.setAdapter(adapter);
		
		Button setIce = (Button)findViewById(R.id.setice);
		
		setIce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getApplicationContext(), DLocation.class);
				startActivity(intent);
				finish();
				
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_ice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater mInflater;
		private int[] item_ratings=new int[3] ;
		TextView tv1,tv;



		public MyCustomAdapter() {
			// TODO Auto-generated constructor stub   
			mInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi=convertView;
			if(convertView==null)
				vi = mInflater.inflate(R.layout.row2, null); 
			tv= (TextView) vi.findViewById(R.id.name);
			tv1= (TextView) vi.findViewById(R.id.number);

			tv.setText("Name :"+ contacts.get(position).getName());
			tv1.setText("Phone No :"+ contacts.get(position).getPhoneNumber());
			
			return vi;
		}



	}
}
