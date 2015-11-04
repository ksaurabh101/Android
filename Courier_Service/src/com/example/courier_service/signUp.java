package com.example.courier_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signUp extends Activity {
	EditText name,email,mobileNo,password1,password2;
	DatabaseHelper helper = new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		name=(EditText)findViewById(R.id.name);
		email=(EditText)findViewById(R.id.email);
		mobileNo=(EditText)findViewById(R.id.mobileNo);
		password1=(EditText)findViewById(R.id.pass1);
		password2=(EditText)findViewById(R.id.pass2);
	}
	public void register(View v)
	{
		if(v.getId()==R.id.signButton)
		{
			String arr[]=new String[5];
			arr[0]=name.getText().toString();
			arr[1]=email.getText().toString();
			arr[2]=mobileNo.getText().toString();
			arr[3]=password1.getText().toString();
			arr[4]=password2.getText().toString();
			if(checkNull(arr, 5))
			{
				Toast temp = Toast.makeText(this , "All Fields Are Required" , Toast.LENGTH_SHORT);
				temp.show();
			}
			else{
				if(verify(arr, 5)==1)
				{
					//register User.....
					if(helper.getUser(arr[1])==1)
					{
						Toast temp = Toast.makeText(this , "Email is already Registered" , Toast.LENGTH_SHORT);
						temp.show();
					}
					else{
						helper.insertUser(arr);
						Toast temp = Toast.makeText(this , "You Are Registered" , Toast.LENGTH_SHORT);
						temp.show();
						
						name.setText("");
						email.setText("");
						mobileNo.setText("");
						password1.setText("");
						password2.setText("");
						
						Intent i = new Intent(signUp.this, MainActivity.class);
						startActivity(i);
					}
				}
			}
		}
	}
public int hasChar(String s,String bag)
	{
		String tem;

		for(int i=0;i<s.length();i++)
		{
			tem=s.substring(i, i+1);
			if(bag.indexOf(tem)<0)
			{
				return 0;
			}
		}
	return 1;
}
public boolean checkNull(String arr[],int n)
	{
		for(int i=0;i<n;i++)
		{
			if(arr[i].equals(null) || arr[i].equals(""))
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
public int verify(String arr[],int n)
	{
		//name verify....
		if(hasChar(arr[0],"abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ")==0)
		{
			Toast temp = Toast.makeText(this , "Enter A Valid Name Numeric or Special Charecters Not Allowed" , Toast.LENGTH_SHORT);
			temp.show();
			return 0;
		}
		//email verify
		if(checkEmail(arr[1])==0)
		{
			Toast temp = Toast.makeText(this , "Enter A Valid Email" , Toast.LENGTH_SHORT);
			temp.show();
			return 0;
		}
		//mobile no verify
		if(hasChar(arr[2], "+-0123456789")==0)
		{
			Toast temp = Toast.makeText(this , "Enter A Valid Mobile No" , Toast.LENGTH_SHORT);
			temp.show();
			return 0;
		}
		//password verify
		if(arr[3].length()<6)
		{
			Toast temp = Toast.makeText(this , "Password Length should be more than 5" , Toast.LENGTH_SHORT);
			temp.show();
			return 0;
		}
		if(!arr[3].equals(arr[4]))
		{
			Toast temp = Toast.makeText(this , "Password Don't Match" , Toast.LENGTH_SHORT);
			temp.show();
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
