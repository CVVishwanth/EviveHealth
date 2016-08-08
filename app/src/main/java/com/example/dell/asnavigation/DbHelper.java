package com.example.dell.asnavigation;

/**
 * Created by dell on 7/31/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    //int Asize;
    private static final String DATABASE_NAME = "Evive.db";
    private static final String TABLE_NAMEEVENT= "myrecords";
    private static final String TABLE_NAMEMEDICINE="mymedicine";
    private static final String COLUMNE_HOSPITALNAME="Hospitalname";
    private static final String COLUMNE__VISITFOR="Visitfor";
    private static final String COLUMNE_DOCTORNAME="Doctorname";
    private static final String COLUMNE_RESULT="Result";
    private static final String COLUMNE_MEDICINE="medicine";
    private static final String COLUMNE_HOUR="hour";
    private static final String COLUMNE_MIN="minutes";
    private static int flag=0;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    public void onCreate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table if not exists " +TABLE_NAMEEVENT+ "(id integer primary key autoincrement,Hospitalname text,Visitfor text,Doctorname text,Result text)");
        db.close();
    }

    public void onCreateMedicine()
    {
        SQLiteDatabase dbm = this.getWritableDatabase();
        dbm.execSQL("create table if not exists " +TABLE_NAMEMEDICINE+ "(id integer primary key autoincrement,medicine text,hour text,minutes text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertdummy()
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMNE_HOSPITALNAME,"Ramakrishna");
        values.put(COLUMNE__VISITFOR,"Fever");
        values.put(COLUMNE_DOCTORNAME,"Suresh kumar");
        values.put(COLUMNE_RESULT,"Medicine");
        db1.insert(TABLE_NAMEEVENT,null,values);
        System.out.println("====insertprofileevent");
        return true;
    }

    public boolean insertProfileevent(String Hospitalname,String Visitfor,String Doctorname,String Result){

        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMNE_HOSPITALNAME,Hospitalname);
        values.put(COLUMNE__VISITFOR,Visitfor);
        values.put(COLUMNE_DOCTORNAME,Doctorname);
        values.put(COLUMNE_RESULT,Result);
        db1.insert(TABLE_NAMEEVENT,null,values);
        System.out.println("====insertprofileevent");
        return true;
    }

public boolean insertmedinevalues(String medicinename,String hour,String min)
{
    /*Toast.makeText(getApplicationContext(),medicinename,Toast.LENGTH_LONG).show();*/
    SQLiteDatabase dbm=this.getWritableDatabase();
    ContentValues values=new ContentValues();
    Log.d("medicinename",medicinename);
    values.put(COLUMNE_MEDICINE,medicinename);
    values.put(COLUMNE_HOUR,hour);
    values.put(COLUMNE_MIN,min);
    dbm.insert(TABLE_NAMEMEDICINE,null,values);
    System.out.println("====insertprofileevent");
    return true;
}


    public ArrayList<HashMap<String, Object>> getAllProfilem() {
        ArrayList<HashMap<String, Object>> arrayListm = new ArrayList<HashMap<String, Object>>();
        SQLiteDatabase dbm = this.getReadableDatabase();
        Cursor cursori = dbm.rawQuery("select * from " + TABLE_NAMEMEDICINE, null);
        cursori.moveToFirst();

        while (cursori.isAfterLast() == false) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("medicine", cursori.getString(1));
            map.put("hour", cursori.getString(2));
            map.put("minutes", cursori.getString(3));
            if (flag == 0) {
                map.put("flag", flag);
                flag = 1;
            } else if (flag == 1) {
                map.put("flag", flag);
                flag = 0;
            }


            arrayListm.add(map);

//            arrayList.add(cursor.getString(1));
            cursori.moveToNext();
        }

        System.out.println("====sizeof arraylistin==="+arrayListm.size());
        return arrayListm;
    }


    public ArrayList<HashMap<String, Object>> getAllProfile() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAMEEVENT, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Hospitalname", cursor.getString(1));
            map.put("Visitfor", cursor.getString(2));
            map.put("Doctorname", cursor.getString(3));
            map.put("Result",cursor.getString(4));
            if (flag == 0) {
                map.put("flag", flag);
                flag = 1;
            } else if (flag == 1) {
                map.put("flag", flag);
                flag = 0;
            }


            arrayList.add(map);

//            arrayList.add(cursor.getString(1));
            cursor.moveToNext();
        }

        System.out.println("====sizeof arraylistin==="+arrayList.size());
        return arrayList;
    }
}