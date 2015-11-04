package com.example.courier_service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Mydb.db";
    
    //user table
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_PASS = "password";
    
    private static final String TABLE_NAME = "users";
    private static final String TABLE_NAME1 = "awbStatus";
    private static final String TABLE_NAME2 = "awbDetail";
    
    //awbDetail table
    private static final String COLUMN_AWB = "awb";
    private static final String COLUMN_PACNAME = "pacname";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DEPARTURE = "departure";
    
    //awbStatus table
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_VALUE = "value";
    
    SQLiteDatabase db;
    
    private static final String TABLE_CREATE = "create table " +TABLE_NAME+"("+COLUMN_NAME+" text not null, "+COLUMN_EMAIL+" text not null, "+COLUMN_MOBILE+" text not null, "+COLUMN_PASS+" text not null)";
    
    private static final String TABLE1_CREATE = "create table " +TABLE_NAME1+"("+COLUMN_AWB+" text not null, "+COLUMN_DATE+" text not null, "+COLUMN_LOCATION+" text not null, "+COLUMN_STATUS+" text not null, "+COLUMN_VALUE+" text not null)";
    
    private static final String TABLE2_CREATE = "create table " +TABLE_NAME2+"("+COLUMN_EMAIL+" text not null, "+COLUMN_AWB+" text not null, "+COLUMN_PACNAME+" text not null, "+COLUMN_PRICE+" text not null, "+COLUMN_DEPARTURE+" text not null)";
    
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    	db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE1_CREATE);
		db.execSQL(TABLE2_CREATE);
		Log.d("Database", "Table is created");
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    	db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        
        this.onCreate(db);
    }
    //method for signUp class
    public void insertUser(String arr[]) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, arr[0]);
        values.put(COLUMN_EMAIL, arr[1]);
        values.put(COLUMN_MOBILE, arr[2]);
        values.put(COLUMN_PASS, arr[3]);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    //method for signUp class
    public int getUser(String email)
    {
        db = this.getReadableDatabase();
        String projection[]={COLUMN_MOBILE,COLUMN_NAME};
        String selection=COLUMN_EMAIL+" LIKE ?";
        String selectionArgs[]={email};
        Cursor cursor=db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int a=cursor.getCount();
        if(a>=1)
        	return 1;
        else
        return 0;
    }
    //method for MainActivity class....for Login
    public String searchPass(String email)
    {
    	db = this.getReadableDatabase();
        String projection[]={COLUMN_EMAIL,COLUMN_PASS};
        String selection=COLUMN_EMAIL+" LIKE ?";
        String selectionArgs[]={email};
        Cursor cursor=db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        
        String a, b;
        b = "not found";
        if(cursor.moveToFirst())
        {
        	a = cursor.getString(0);
            if(a.equalsIgnoreCase(email))
            {
                b = cursor.getString(1);
            }
        }
        return b;
    }
    //method for RemoveUser class
    public void removeUser(String email)
    {
    	SQLiteDatabase dbw,dbr;
    	dbw = this.getWritableDatabase();
        String whereArgs[]={email};
        dbw.delete(TABLE_NAME, COLUMN_EMAIL+ "=?", whereArgs);
        
        dbr = this.getReadableDatabase();
        String projection[]={COLUMN_EMAIL,COLUMN_AWB};
		String selection=COLUMN_EMAIL+" LIKE ?";
		String selectionArgs[]={email};
		Cursor cursor=dbr.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
        String a,b;
        if(cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);

                if(a.equalsIgnoreCase(email))
                {
                	b = cursor.getString(1);
                    whereArgs[0]=b;
                    dbw.delete(TABLE_NAME1, COLUMN_AWB+ "=?", whereArgs);
                }
            }
            while(cursor.moveToNext());
        }
		whereArgs[0]=email;
        dbw.delete(TABLE_NAME2, COLUMN_EMAIL+ "=?", whereArgs);
    }

    
    //display class
    public String[] getNameAndMobile(String email)
    {
    	String arr[]=new String[2];
   	 	db = this.getReadableDatabase();
        String projection[]={COLUMN_NAME,COLUMN_MOBILE};
        String selection=COLUMN_EMAIL+" LIKE ?";
        String selectionArgs[]={email};
        Cursor cursor=db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
       if(cursor.moveToFirst())
       {
           arr[0]=cursor.getString(0);
           arr[1]=cursor.getString(1);
       }
   	return arr;
    }
    
  
  //display class
  	public ArrayList<String> getAllAWBDetails(String email)
  	{
  		ArrayList<String> awbNo = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_AWB};
  		String selection=COLUMN_EMAIL+" LIKE ?";
  		String selectionArgs[]={email};
  		Cursor cursor=db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  awbNo.add(cursor.getString(0));
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			awbNo.add("NO");
  		}
  		return awbNo;
  	}
  //display class
  	public int getAllAWBCount(String email)
  	{
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_AWB};
  		String selection=COLUMN_EMAIL+" LIKE ?";
  		String selectionArgs[]={email};
  		Cursor cursor=db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
          int a=cursor.getCount();
  		return a;
  	}
  //display class
  	public ArrayList<String> getAllNameDetails(String email)
  	{
  		ArrayList<String> name = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_PACNAME};
  		String selection=COLUMN_EMAIL+" LIKE ?";
  		String selectionArgs[]={email};
  		Cursor cursor=db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  name.add(cursor.getString(0));
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			name.add("NO");
  		}
  		return name;
  	}
  //display class
  	public ArrayList<String> getAllPriceDetails(String email)
  	{
  		ArrayList<String> price = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_PRICE};
  		String selection=COLUMN_EMAIL+" LIKE ?";
  		String selectionArgs[]={email};
  		Cursor cursor=db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  price.add(cursor.getString(0));
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			price.add("NO");
  		}
  		return price;
  	}
  	
  //DisplayStatus class
  	public ArrayList<String> getAllDate(String awb)
  	{
  		ArrayList<String> date = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_DATE};
  		String selection=COLUMN_AWB+" LIKE ?";
  		String selectionArgs[]={awb};
  		Cursor cursor=db.query(TABLE_NAME1, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  date.add(cursor.getString(0));
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			date.add("NO");
  		}
  		return date;
  	}
  	 //DisplayStatus class
  	public ArrayList<String> getAllLocation(String awb)
  	{
  		ArrayList<String> location = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_LOCATION};
  		String selection=COLUMN_AWB+" LIKE ?";
  		String selectionArgs[]={awb};
  		Cursor cursor=db.query(TABLE_NAME1, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  location.add(cursor.getString(0));
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			location.add("NO");
  		}
  		return location;
  	}
  	 //DisplayStatus class
  	public ArrayList<String> getAllStatus(String awb)
  	{
  		ArrayList<String> status = new ArrayList<String>();
  		
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_DATE,COLUMN_LOCATION,COLUMN_STATUS};
  		String selection=COLUMN_AWB+" LIKE ?";
  		String selectionArgs[]={awb};
  		Cursor cursor=db.query(TABLE_NAME1, projection, selection, selectionArgs, null, null, COLUMN_VALUE);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
            	  String s=cursor.getString(0)+"-"+cursor.getString(1)+"-"+cursor.getString(2);
                  status.add(s);
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			status.add("NO");
  		}
  		return status;
  	}
    
  //For AddPackage class ..method
  	public void insertPackageDetails(String arr[]) {
          db = this.getWritableDatabase();
          ContentValues values = new ContentValues();

          values.put(COLUMN_EMAIL, arr[0]);
          values.put(COLUMN_AWB, arr[1]);
          values.put(COLUMN_PACNAME, arr[2]);
          values.put(COLUMN_PRICE, arr[3]);
          values.put(COLUMN_DEPARTURE, arr[4]);

          db.insert(TABLE_NAME2, null, values);
          db.close();
      }
  	//For AddPackage class ..method
  	public int getAwb(String awb)
      {
  		db = this.getReadableDatabase();
        String projection[]={COLUMN_EMAIL};
        String selection=COLUMN_AWB+" LIKE ?";
		String selectionArgs[]={awb};
		Cursor cursor=db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
        int a=cursor.getCount();
        if(a>=1)
        	return 1;
        return 0;
      }
  	//For UpdatePackage class.....
  	public int getPackageValue(String awb)
  	{
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_VALUE};
  		String selection=COLUMN_AWB+" LIKE ?";
  		String selectionArgs[]={awb};
  		Cursor cursor=db.query(TABLE_NAME1, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		return a;
  	}
  //For UpdatePackage class.....
  	public void insertPackageUpdate(String arr[]) 
  	{
  		int a=getPackageValue(arr[0]);
  		db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_AWB, arr[0]);
        values.put(COLUMN_DATE, arr[1]);
        values.put(COLUMN_LOCATION, arr[2]);
        values.put(COLUMN_STATUS, arr[3]);
        values.put(COLUMN_VALUE, a+1);
        db.insert(TABLE_NAME1, null, values);
        db.close();
    }
  	//For RemovePackage class .......
  	public void removePackage(String email,String awb)
  	{
  		db = this.getWritableDatabase();
          String whereArgs[]={awb};
          db.delete(TABLE_NAME1, COLUMN_AWB+ "=?", whereArgs);
          
          db.delete(TABLE_NAME2, COLUMN_AWB+ "=?", whereArgs);
  	}
  	
  	//for DisplayUser class...method
  	public ArrayList<String> getAllUsers()
  	{
  		ArrayList<String> arr=new ArrayList<String>();
  		db = this.getReadableDatabase();
  		String projection[]={COLUMN_NAME,COLUMN_EMAIL};
  		String selection="";
  		String selectionArgs[]={};
  		Cursor cursor=db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
  		int a=cursor.getCount();
  		if(a>0)
  		{
          if(cursor.moveToFirst())
          {
              do{
                  String s=cursor.getString(0)+" - "+cursor.getString(1);
                  arr.add(s);
              }
              while(cursor.moveToNext());
          }
  		}
  		else{
  			arr.add("NO User");
  		}
  		return arr;
  	}
  	public int getAllUsersCount()
  	{
  		int a;
  		db = this.getReadableDatabase();
        String query = "select "+COLUMN_NAME+", "+COLUMN_EMAIL+" from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query , null);
        a=cursor.getCount();
  		return a;
  	}
  	
  			
}