package com.example.courier_service;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayUser extends Activity{
	DatabaseHelper helper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayuser);
		
		try{
			ArrayList<String> arr=helper.getAllUsers();
			ListView userlistview=(ListView)findViewById(R.id.displayUserlistView);
			
			ArrayAdapter<String> userAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
			userlistview.setAdapter(userAdapter);
		}catch(Exception e)
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
