package com.example.courier_service;

import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText email,pass;
	DatabaseHelper helper = new DatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		email=(EditText)findViewById(R.id.email);
		pass=(EditText)findViewById(R.id.password);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		createDialog();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Process.killProcess(Process.myPid());
		super.onDestroy();
	}

	private void createDialog() {
		AlertDialog.Builder alertDlg=new AlertDialog.Builder(this);
		alertDlg.setMessage("Are You Sure You Want To Exit");
		alertDlg.setCancelable(false);
		alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				//MainActivity.super.onBackPressed();
				finish();
			}
		});
		alertDlg.setNegativeButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		alertDlg.create().show();
	}

	public void login(View v)
	{
		if(v.getId()==R.id.loginButton)
		{
			String emailstr=email.getText().toString();
			String passstr=pass.getText().toString();
			String arr[]=new String[2];
			arr[0]=emailstr;
			arr[1]=passstr;
			if(emailstr.equalsIgnoreCase("admin") && passstr.equals("admin"))
			{
				email.setText("");
				pass.setText("");
				Intent i = new Intent(MainActivity.this, Admin.class);
				startActivity(i);
			}
			else{
				if(checkNull(arr, 2))
				{
					Toast temp = Toast.makeText(this , "Both Fields Are Required" , Toast.LENGTH_SHORT);
					temp.show();
				}
				else{
					if(checkEmail(arr[0])==0)
					{
						Toast temp = Toast.makeText(this , "Enter A Valid Email" , Toast.LENGTH_SHORT);
						temp.show();
					}
					else
					{
						if(helper.getUser(emailstr)==0)
						{
							Toast temp = Toast.makeText(this , "Email is Not Registered" , Toast.LENGTH_SHORT);
							temp.show();
						}
						else{
							String password=helper.searchPass(emailstr);
							if(passstr.equals(password))
							{
								email.setText("");
								pass.setText("");
								Intent i = new Intent(MainActivity.this, Display.class);
								i.putExtra("EMAIL",emailstr);
								startActivity(i);
							}
							else{
								Toast temp = Toast.makeText(this , "Password is Not correct" , Toast.LENGTH_SHORT);
								temp.show();
							}
						}
					}
				}
			}
			
		}
	}
	public void signUp(View v)
	{
		if(v.getId()==R.id.signUpButton)
		{
			Intent i = new Intent( MainActivity.this, signUp.class);
            startActivity(i);
		}
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
}
