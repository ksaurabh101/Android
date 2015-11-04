package com.example.courier_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePackage extends Activity{

	DatabaseHelper helper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepackage);
	}
	public void updatePackage(View v)
	{
		if(v.getId()==R.id.updatePackageButton)
		{
			EditText awb=(EditText)findViewById(R.id.awbUpdate);
			EditText date=(EditText)findViewById(R.id.dateUpdate);
			EditText location=(EditText)findViewById(R.id.locationUpdate);
			EditText status=(EditText)findViewById(R.id.statusUpdate);
			
			String arr[]=new String[4];
			arr[0]=awb.getText().toString();
			arr[1]=date.getText().toString();
			arr[2]=location.getText().toString();
			arr[3]=status.getText().toString();
			
			if(checkNull(arr, 4))
			{
				Toast temp = Toast.makeText(this , "All Fields Are Required" , Toast.LENGTH_SHORT);
				temp.show();
			}
			else{
				helper.insertPackageUpdate(arr);
				Toast temp = Toast.makeText(this , "Data is Updated" , Toast.LENGTH_SHORT);
				temp.show();
				awb.setText("");
				date.setText("");
				location.setText("");
				status.setText("");
				
				Intent i = new Intent(UpdatePackage.this, Admin.class);
	            startActivity(i);
			}
		}
	}
	public boolean checkNull(String arr[],int n)
	{
		for(int i=0;i<n;i++)
		{
			if(arr[i]==null || arr[i]=="")
				return true;
		}
		return false;
	}
	public void home(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
	}
}
