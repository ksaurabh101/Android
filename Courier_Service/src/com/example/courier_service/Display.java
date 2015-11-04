package com.example.courier_service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity {
	DatabaseHelper helper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		final String emailstr=getIntent().getStringExtra("EMAIL");
		TextView name=(TextView)findViewById(R.id.nametextView);
		TextView mobile=(TextView)findViewById(R.id.mobiletextView);
		TextView email=(TextView)findViewById(R.id.emailtextView);
		
		final ListView awblistview=(ListView)findViewById(R.id.awblistView);
		ListView namelistview=(ListView)findViewById(R.id.namelistView);
		ListView pricelistview=(ListView)findViewById(R.id.pricelistView);
		
		try{
			String arr[]=helper.getNameAndMobile(emailstr);
			name.setText("Hello "+arr[0]);
			mobile.setText(arr[1]);
			email.setText(emailstr);
		}
		catch(Exception e)
		{
			Toast temp = Toast.makeText(this , "Error:" +e.getMessage() , Toast.LENGTH_SHORT);
			temp.show();
		}
		
		try{
			ArrayList<String> awbNoList =helper.getAllAWBDetails(emailstr);
			ArrayList<String> nameList = helper.getAllNameDetails(emailstr);
			ArrayList<String> priceList =helper.getAllPriceDetails(emailstr);
			
			ArrayAdapter<String> awbAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,awbNoList);
			ArrayAdapter<String> nameAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameList);
			ArrayAdapter<String> priceAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,priceList);
			
			awblistview.setAdapter(awbAdapter);
			namelistview.setAdapter(nameAdapter);
			pricelistview.setAdapter(priceAdapter);
			
			awblistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int pos,
						long id) {
					// TODO Auto-generated method stub
					String value=(String)awblistview.getItemAtPosition(pos);
					try{
						Intent i = new Intent(Display.this, DisplayStatus.class);
						i.putExtra("AWB",value);
						i.putExtra("EMAIL", emailstr);
						startActivity(i);
					}catch(Exception e)
					{
						Toast temp = Toast.makeText(getApplicationContext(), "Error:" +e.getMessage(), Toast.LENGTH_SHORT);
						temp.show();
					}
				}
			});
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
