package com.example.dexter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonElement;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class DLocation extends Activity {

	Button select;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dlocation);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		final TextView location = (TextView)findViewById(R.id.destination);

		// adding

		select = (Button) findViewById(R.id.fetch);

		select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
			System.out.println("Clicked...calling");
						
			Intent intent = new Intent(getApplicationContext(), Tracker.class);
			
			intent.putExtra("destination", "Banashankari,Banagalore");
			
			startActivity(intent);
			
			finish();
			
			}
					
		});



	}
	//@Override
	//public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	//    ma.toggle(arg2);
	//}

	

	public  void getAllContacts(ContentResolver cr) {

		System.out.println("Gets contacts");

		Cursor phones = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null,null, null);

		if (phones.getCount() > 0) {

			while (phones.moveToNext()) {

				String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));

				String name=phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));



				if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",	    
							new String[]{id}, null);

					while (pCur.moveToNext()) {

						String phone_number  = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						System.out.println("Name is "+name+" no "+phone_number);

						//	ice_contacts.add(new ICE_Contact(name, phone_number));


					} 

					pCur.close();

				}
			}
		}

		phones.close();
	}

	
	/*
    class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
    {  private SparseBooleanArray mCheckStates;
       LayoutInflater mInflater;
        TextView tv1,tv;
        CheckBox cb;
        MyAdapter()
        {
            mCheckStates = new SparseBooleanArray(ice_contacts.size());
            mInflater = (LayoutInflater)DLocation.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ice_contacts.size();
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
             vi = mInflater.inflate(R.layout.row, null); 
             tv= (TextView) vi.findViewById(R.id.textView1);
             tv1= (TextView) vi.findViewById(R.id.textView2);
             cb = (CheckBox) vi.findViewById(R.id.checkBox1);
             tv.setText("Name :"+ ice_contacts.get(position).name);
             tv1.setText("Phone No :"+ ice_contacts.get(position).phone_number);
             cb.setTag(position);
             cb.setChecked(mCheckStates.get(position, false));
             cb.setOnCheckedChangeListener(this);

            return vi;
        }
         public boolean isChecked(int position) {
                return mCheckStates.get(position, false);
            }

            public void setChecked(int position, boolean isChecked) {
                mCheckStates.put(position, isChecked);
            }

            public void toggle(int position) {
                setChecked(position, !isChecked(position));
            }
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
            // TODO Auto-generated method stub
        	 mCheckStates.put((Integer) buttonView.getTag(), isChecked);
        	/*if(no_checked >= 3 ) {

        		buttonView.setChecked(false);

        		Toast.makeText(getApplicationContext(), "Max 3 contacts only",Toast.LENGTH_SHORT).show();

        	}else {

        		mCheckStates.put((Integer) buttonView.getTag(), isChecked);

        		no_checked++;

        		System.out.println(no_checked);
        	}
        }   
    }   */
}