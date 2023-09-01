package com.example.saloonapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterDb extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;


    public static final String DATABASE_NAME = "salon19090.db";
    public static final String TABLE_NAME = "salonreg";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "username";
    public static final String COL_4 = "email";
    public static final String COL_5 = "phonenumber";
    public static final String COL_6 = "password";
    public static final String COL_7 = "cfpassword";

    //Create User Table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            COL_2 + " TEXT," +
                            COL_3 + " TEXT," +
                            COL_4 + " TEXT," +
                            COL_5 + " TEXT," +
                            COL_6 + " TEXT,"+
                            COL_7 + " TEXT)";

    //Drop User Table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public LoginRegisterDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginRegisterDb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    //Insert Users into the table
    public boolean insertUser(String name, String username, String email, String phonenumber, String password, String cfpassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phonenumber);
        contentValues.put(COL_6, password);
        contentValues.put(COL_7 ,cfpassword);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Read User Table using username
    public Cursor checkUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM '" + TABLE_NAME + "' WHERE username = '" + username + "'", null);
         return res;
    }

    //Read All User Details
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    //Delete User
    public void DeleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_3 + " LIKE ?";
        String[] selectionArgs = {username};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public static class Home extends AppCompatActivity {

        String username;
        String email;
        String emailnew;
        EditText mTextUsername;
        ImageView mviewbeautytips;
        ImageView mviewwomenstyle;
        ImageView mviewappoinment;
        ImageView mviewcontact;

        String styleButtonID;

        ImageView mviewhair,mviewbeauty,mviewbridal;


        String intentemail,intentlocation,location;
        Animation fade_in, fade_out;
        ViewFlipper viewFlipper;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.txt_layout);

            viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);

            fade_in = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_in);
            fade_out = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_out);

            viewFlipper.setInAnimation(fade_in);
            viewFlipper.setOutAnimation(fade_out);

            //sets auto flipping
            viewFlipper.setAutoStart(true);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();

            mviewhair = (ImageView) findViewById(R.id.imageButtonwomen20);
            mviewbeauty = (ImageView) findViewById(R.id.imageButtonthreading);
            mviewbridal = (ImageView) findViewById(R.id.imageButtonpiercing);


            mviewbeautytips = (ImageView) findViewById(R.id.imageView3);
            mviewwomenstyle = (ImageView) findViewById(R.id.imageView4);
            mviewappoinment = (ImageView) findViewById(R.id.imageView6);
            mviewcontact = (ImageView) findViewById(R.id.imageView7);



            username = getIntent().getStringExtra("EXTRA_USERNAME");
            intentemail = getIntent().getStringExtra("Email_home");
            emailnew = getIntent().getStringExtra("EXTRA_email_selectdate");
//            intentlocation = getIntent().getStringExtra("INTENT_LOCATION");



            mviewbeautytips.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this, NewBeautyTips.class);
                    startActivity(intent);
                }
            } );

            mviewwomenstyle.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Home.this, WomenStyle.class);
                    startActivity(intent1);
                }
            } );

            mviewcontact.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(Home.this, Contact_Us.class);
                    //intent3.putExtra("INTENT_LOCATION", intentlocation);
                    startActivity(intent3);
                }
            } );


            mviewhair.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent4 = new Intent(Home.this, Hair.class);
                    startActivity(intent4);
                }
            } );

            mviewbeauty.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent5 = new Intent(Home.this, Beauty.class);
                    startActivity(intent5);
                }
            } );

            mviewbridal.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent6 = new Intent(Home.this, Bridal.class);
                    startActivity(intent6);
                }
            } );

            mviewappoinment.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Home.this, Apoinment.class);
                    intent1.putExtra("EXTRA_USERNAME_HOME", username);
                    //intent1.putExtra("EXTRA_email", "sachintha@gmail.com");
                    startActivity(intent1);
                }
            } );



            /*editprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(Home.this, EditProfile.class);
                    startActivity(registerIntent);
                }

            });*/
        }


        //side menu icon
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_home, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            try{
                switch (item.getItemId()) {
                    case R.id.home:
                        try{
                         Intent intent1 = new Intent(Home.this, ProfileSetting.class);
                            intent1.putExtra("EXTRA_USERNAME_HOME", username);
                            intent1.putExtra("EXTRA_email_home", emailnew);
                            startActivity(intent1);
                            return true;
                        }catch (Exception e){
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }


    }
}
