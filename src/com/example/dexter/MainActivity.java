package com.example.dexter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemClickListener{

	List<ICE_Contact> ice_contacts = new ArrayList<ICE_Contact>();

	MyAdapter ma ;
	Button select;
	int no_checked = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getAllContacts(this.getContentResolver());
		ListView lv= (ListView) findViewById(R.id.lv);
		ma = new MyAdapter();
		lv.setAdapter(ma);
		lv.setOnItemClickListener(this); 
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		// adding
		select = (Button) findViewById(R.id.button1);

		select.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				StringBuilder checkedcontacts= new StringBuilder();

				for(int i = 0; i < ice_contacts.size(); i++)

				{
					if(ma.mCheckStates.get(i)==true)
					{
						
						//write ice contacts to db
						ContactsDB db = new ContactsDB(getApplicationContext());

						// Inserting Contacts
						Log.d("Insert: ", "Inserting .."); 
						db.addContact(ice_contacts.get(i));        


						//Globals.ice_contacts.add(ice_contacts.get(i));
						//sendSMS(ice_contacts.get(i).phone_number, "Narayana :-) Aja!");
						//checkedcontacts.append(name1.get(i).toString());
						//checkedcontacts.append("\n");

					}

				}

				Toast.makeText(MainActivity.this, checkedcontacts,1000).show();
				setICEContacts();
			}

			private void setICEContacts() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ListICE.class);
				startActivity(intent);
				finish();
			}       
		});


	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ma.toggle(arg2);
	}

	private void sendSMS(String phoneNumber, String message)
	{
		SmsManager sms = SmsManager.getDefault();
		System.out.println(phoneNumber+" "+message);
		//sms.sendTextMessage("9538729218", null, message, null, null);
	}

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
						
						ice_contacts.add(new ICE_Contact(name,phone_number));

					} 

					pCur.close();

				}
			}
		}

		phones.close();
	}




	class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
	{  private SparseBooleanArray mCheckStates;
	LayoutInflater mInflater;
	TextView tv1,tv;
	CheckBox cb;
	MyAdapter()
	{
		mCheckStates = new SparseBooleanArray(ice_contacts.size());
		mInflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		tv.setText("Name :"+ ice_contacts.get(position).getName());
		tv1.setText("Phone No :"+ ice_contacts.get(position).getPhoneNumber());
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
        	}*/
	}   
	}   
}