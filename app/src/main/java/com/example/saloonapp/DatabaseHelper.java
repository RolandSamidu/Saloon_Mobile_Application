package com.example.saloonapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "salon19090.db";
    public static final String TABLE_NAME = "salonreg";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "username";
    public static final String COL_4 = "email";
    public static final String COL_5 = "phonenumber";
    public static final String COL_6 = "password";
    public static final String COL_7 = "cfpassword";

    //public static final String COL_7 = "image";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, email TEXT, phonenumber TEXT, password TEXT, image BOLB)");
        sqLiteDatabase.execSQL("CREATE TABLE salonreg (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, email TEXT, phonenumber TEXT, password TEXT, cfpassword TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

   // public long addUser(String name, String username, String email, String phonenumber, String password, byte[] image) {
        public long addUser(String name, String username, String email, String phonenumber, String password, String cfpassword) {
        //public long addUser(String name, String username, String email, String phonenumber, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phonenumber", phonenumber);
        contentValues.put("password", password);
        contentValues.put("cfpassword" , cfpassword);
        //contentValues.put("image", image);
        long res = db.insert("salonreg", null, contentValues);
        db.close();
        return res;
    }


    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_3 + "=?" + " and " + COL_6 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();


        if (count > 0)
            return true;
        else
            return false;
    }


    public Cursor getdata(String userName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + TABLE_NAME + " where username = '" + userName + "'", null);
        return cursor;

    }




   /* public Boolean updateuserdata(String name, String username, String email, String phonenumber) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phonenumber", phonenumber);
        //contentValues.put("password", password);
        //contentValues.put("image", image);
        Cursor cursor = DB.rawQuery("Select * from " + TABLE_NAME + " where username = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update(TABLE_NAME, contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}
*/



/*

    public long updateUser(String name, String email, String phonenumber, String password){
        //public long addUser(String name, String username, String email, String phonenumber, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        //contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("phonenumber", phonenumber);
        contentValues.put("password", password);
        //contentValues.put("image", image);

        long res = db.update(TABLE_NAME,contentValues, " name = " + name , null);


        db.close();
        return res;
    }
*/



}
