package com.bestbaan.moonbox.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbUtil extends SQLiteOpenHelper {
	public SQLiteDatabase db;
	public boolean debug = false;
	private static final String name = "MBUI4"; // 数据库名称
	private static final int version = 1; // 数据库版本
	private static final String LIFE_JSONKEY = "lifejson"; // 数据库版本


	public DbUtil(Context context) {
		super(context, name, null, version);

		// TODO Auto-generated constructor stub
	}

	public SQLiteDatabase getDb() {
		return getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("CREATE TABLE IF NOT EXISTS index_app_info (id integer primary key autoincrement,pkgname varchar(255) UNIQUE,orderid integer(11))");
		db.execSQL("CREATE TABLE IF NOT EXISTS json_cache (id integer primary key autoincrement,jsonkey varchar(255) UNIQUE,jsonstr TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS App_List (id integer primary key autoincrement,pkgname varchar(255),tag varchar(255))");
	
	}
   
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
                
	}
	public void delShowApp(String Tag){
		try {
			db=getReadableDatabase();
			db.execSQL("DELETE FROM App_List where tag=?",new String[]{Tag});
			db.close();
	
    	}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void saveShowApp(List<String> pkglist,String Tag){
		try {
			db=getReadableDatabase();
			for(int i=0;i<pkglist.size();i++){
				db.execSQL("INSERT INTO App_List(pkgname,tag) values(?,?)",new Object[]{pkglist.get(i),Tag});
			}
			
			db.close();
	
    	}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public List<String> GetAppByTag(String Tag){
		List<String> list=new ArrayList<String>();
		try {
			db=getReadableDatabase();
			Cursor Row =db.rawQuery("Select pkgname from App_List where tag=?",new String[]{Tag});
			while (Row.moveToNext()) {
				list.add(Row.getString(0));
			}
			
			
			db.close();
		
    	}catch (Exception e) {
			// TODO: handle exception
    		
		}
		return list;
	} 
	public void delAppindex(String pkgname){
		try {
			db=getReadableDatabase();
			db.execSQL("DELETE FROM index_app_info where pkgname=?",new String[]{pkgname});
			db.close();
	
    	}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void SaveLifeJson(String jsonstr){
		try {
			db=getReadableDatabase();
			db.execSQL("REPLACE INTO json_cache(jsonkey,jsonstr) values(?,?)",new Object[]{LIFE_JSONKEY,jsonstr});
			db.close();
	
    	}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public String GetLifeJson(){
    	try {
			db=getReadableDatabase();
			Cursor Row =db.rawQuery("Select jsonstr from json_cache where jsonkey=?",new String[]{LIFE_JSONKEY});
			if(Row.moveToFirst()){
				String jsonstr=Row.getString(0);
				
				if(debug){
					Log.d("Row0","Row0:"+Row.getString(0));
				
				}
				return jsonstr;
			}
			db.close();
		
    	}catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
    	return null;
    }
	
    public void SaveAppIndex(String pkgname,int index){
    	try {
			db=getReadableDatabase();
			db.execSQL("REPLACE INTO index_app_info(pkgname,orderid) values(?,?)",new Object[]{pkgname,index});
			db.close();
	
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
    public int GetAppIndex(String pkgname){
    	try {
			db=getReadableDatabase();
			Cursor Row =db.rawQuery("Select * from index_app_info where pkgname=?",new String[]{pkgname});
			if(Row.moveToFirst()){
				String index=Row.getString(2);
				
				if(debug){
					Log.d("Row0","Row0:"+Row.getString(0));
					Log.d("Row1","Row1:"+Row.getString(1));
					Log.d("Row12","Row2:"+Row.getString(2));
				}
				return Integer.parseInt(index);
			}
			db.close();
		
    	}catch (Exception e) {
			// TODO: handle exception
    		return -1;
		}
    	return -1;
    }



}
