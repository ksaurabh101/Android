package com.example.courier_service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RemovePackage extends Activity {
	DatabaseHelper helper = new DatabaseHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.removepackage);
	}

	public void removePackage(View v) {
		if (v.getId() == R.id.removepackagebutton) {
			EditText email = (EditText) findViewById(R.id.removePackageEmail);
			EditText awb = (EditText) findViewById(R.id.removePackageAWB);
			String emailstr = email.getText().toString();
			String awbstr = awb.getText().toString();
			String arr[] = { emailstr, awbstr };
			if (checkNull(arr, 2)) {
				Toast temp = Toast.makeText(this, "All Fields Are Required",
						Toast.LENGTH_SHORT);
				temp.show();
			} else {
				if (checkEmail(arr[0]) == 0) {
					Toast temp = Toast.makeText(this, "Enter A Valid Email",
							Toast.LENGTH_SHORT);
					temp.show();
				} else {
					createDialog();
				}
			}
		}
	}

	public boolean checkNull(String arr[], int n) {
		for (int i = 0; i < n; i++) {
			if (arr[i].equals(null) || arr[i].equals(""))
				return true;
		}
		return false;
	}

	public int checkEmail(String s) {
		int i = 0;
		while (i < s.length() && s.charAt(i) != '@')
			i++;
		if (i >= s.length() || s.charAt(i) != '@') {
			return 0;
		} else {
			i += 2;
		}
		while (i < s.length() && s.charAt(i) != '.')
			i++;
		if (i >= s.length() || s.charAt(i) != '.') {
			return 0;
		}
		return 1;
	}

	private void createDialog() {
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		alertDlg.setMessage("Are You Sure You Want To Remove");
		alertDlg.setCancelable(false);
		alertDlg.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						EditText email = (EditText) findViewById(R.id.removePackageEmail);
						EditText awb = (EditText) findViewById(R.id.removePackageAWB);
						String emailstr = email.getText().toString();
						String awbstr = awb.getText().toString();
						helper.removePackage(emailstr, awbstr);// remove datafrom table awbdetails and awbstatus
						email.setText("");
						awb.setText("");
					}
				});
		alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				EditText email = (EditText) findViewById(R.id.removePackageEmail);
				EditText awb = (EditText) findViewById(R.id.removePackageAWB);
				email.setText("");
				awb.setText("");
			}
		});
		alertDlg.create().show();
	}

	public void home(View v) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
