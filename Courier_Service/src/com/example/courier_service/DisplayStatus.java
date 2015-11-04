package com.example.courier_service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayStatus extends Activity{
	DatabaseHelper helper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystatus);
		try{
			String awb=getIntent().getStringExtra("AWB");
			String email=getIntent().getStringExtra("EMAIL");
			
			TextView emailTextview=(TextView)findViewById(R.id.textViewemail);
			TextView awbTextview=(TextView)findViewById(R.id.textViewawb);
			
			emailTextview.setText(email);
			awbTextview.setText(awb);
			
			//ListView datelistview=(ListView)findViewById(R.id.datelistView);
			//ListView locationlistview=(ListView)findViewById(R.id.locationlistView);
			final ListView statuslistview=(ListView)findViewById(R.id.statuslistView);
			
			//ArrayList<String> dateList =helper.getAllDate(awb);
			//ArrayList<String> locationList = helper.getAllLocation(awb);
			ArrayList<String> statusList =helper.getAllStatus(awb);
			
			//ArrayAdapter<String> dateAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dateList);
			//ArrayAdapter<String> locationAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,locationList);
			ArrayAdapter<String> statusAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,statusList);
			
			//datelistview.setAdapter(dateAdapter);
			//locationlistview.setAdapter(locationAdapter);
			statuslistview.setAdapter(statusAdapter);
		}
		catch(Exception e)
		{
			Toast temp = Toast.makeText(this , "Error:" +e.getMessage() , Toast.LENGTH_SHORT);
			temp.show();
		}
	}
	public void home(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
	}
}
