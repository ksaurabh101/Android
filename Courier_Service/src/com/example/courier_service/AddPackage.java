package com.example.courier_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPackage extends Activity{

	DatabaseHelper helper=new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpackage);
	}
	public void addPackage(View v)
	{
		if(v.getId()==R.id.addPackageButton)
		{
			EditText email=(EditText)findViewById(R.id.emailaddpackage);
			EditText awb=(EditText)findViewById(R.id.awbnoaddpackage);
			EditText name=(EditText)findViewById(R.id.productnameaddpackage);
			EditText price=(EditText)findViewById(R.id.priceaddpackage);
			EditText date=(EditText)findViewById(R.id.dateaddpackage);
			
			String arr[]=new String[5];
			arr[0]=email.getText().toString();
			arr[1]=awb.getText().toString();
			arr[2]=name.getText().toString();
			arr[3]=price.getText().toString();
			arr[4]=date.getText().toString();
			
			if(checkNull(arr, 5))
			{
				Toast temp = Toast.makeText(this , "All Fields Are Required" , Toast.LENGTH_SHORT);
				temp.show();
			}
			else{
				if(checkEmail(arr[0])==0)
				{
					Toast temp = Toast.makeText(this , "Enter A Valid Email" , Toast.LENGTH_SHORT);
					temp.show();
				}
				else{
					
					if(helper.getAwb(arr[1])==1)
					{
						Toast temp = Toast.makeText(this , "This awb no is already available" , Toast.LENGTH_SHORT);
						temp.show();
						Intent i = new Intent(AddPackage.this, Admin.class);
			            startActivity(i);
					}
					else{
						helper.insertPackageDetails(arr);
						Toast temp = Toast.makeText(this , "Data is inserted" , Toast.LENGTH_SHORT);
						temp.show();
						email.setText("");
						awb.setText("");
						name.setText("");
						price.setText("");
						date.setText("");
						Intent i = new Intent(AddPackage.this, Admin.class);
			            startActivity(i);
					}
					
				}
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
	public int checkEmail(String s)
	{
		int i=0;
		while(i<s.length() && s.charAt(i)!='@')
			i++;
		if(i>=s.length() || s.charAt(i)!='@')
		{
			return 0;
		}
		else{
			i+=2;
		}
		while(i<s.length() && s.charAt(i)!='.')
			i++;
		if(i>=s.length() || s.charAt(i)!='.')
		{
			return 0;
		}
		return 1;
	}
	public void home(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
	}
}
