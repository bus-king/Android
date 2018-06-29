package com.example.paeng.busking.InnerDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by paeng on 2018. 4. 2..
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHOWLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, showName TEXT, showTitle TEXT, showLocation TEXT, showGenre INTEGER, showHeart INTEGER, showTime TEXT, showDescription TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertShow(String userId, String showName, String showTitle, String showLocation, int showGenre, int showHeart, String showTime, String showDescription){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SHOWLIST VALUES(null, '" + userId + "','" + showName + "', '" + showTitle + "', '" + showLocation + "' , '"+ showGenre + "' , '"+ showHeart + "' , '"+ showTime +"' , '"+ showDescription + "');");
        db.close();
    }


    public String[] getResultShowList(){

        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String[] resultList = new String[1000];

        Cursor cursor = db.rawQuery("SELECT * FROM SHOWLIST", null);
        int i = 0;
        while (cursor.moveToNext()) {
            result = cursor.getString(0)
                    + "/"
                    + cursor.getString(1)
                    + "/"
                    + cursor.getString(2)
                    + "/"
                    + cursor.getString(3)
                    + "/"
                    + cursor.getString(4)
                    + "/"
                    + cursor.getString(5)
                    + "/"
                    + cursor.getString(6)
                    + "/"
                    + cursor.getString(7)
                    + "/"
                    + cursor.getString(8);

            resultList[i] = result;
            i++;
        }
        return resultList;
    }


}
