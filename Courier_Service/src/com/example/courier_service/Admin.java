package com.example.courier_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Admin extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
	}
	public void update(View v)
	{
		if(v.getId()==R.id.removeUser)
		{
			Intent i = new Intent(Admin.this, RemoveUser.class);
            startActivity(i);
		}
		if(v.getId()==R.id.addpackage)
		{
			Intent i = new Intent(Admin.this, AddPackage.class);
            startActivity(i);
		}
		if(v.getId()==R.id.removepackage)
		{
			Intent i = new Intent(Admin.this, RemovePackage.class);
            startActivity(i);
		}
		if(v.getId()==R.id.updatepackagestatus)
		{
			Intent i = new Intent(Admin.this, UpdatePackage.class);
            startActivity(i);
		}
		if(v.getId()==R.id.displayUsers)
		{
			Intent i = new Intent(Admin.this, DisplayUser.class);
            startActivity(i);
		}
	}
	public void home(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
	}
}
