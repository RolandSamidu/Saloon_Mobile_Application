package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProfileSetting extends AppCompatActivity {

    TextView mTextViewupdate;
    TextView mTextViewdelet;
    TextView mTextViewLogout;
    TextView mTextAddLocation;
    EditText mTextUsername;
    String intentusername;
    String intentemail;
    String email,emailnewone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        intentusername = getIntent().getStringExtra("EXTRA_USERNAME_HOME");
        mTextViewupdate = (TextView) findViewById(R.id.textViewUpdateProfile);
        mTextViewdelet = (TextView) findViewById(R.id.textViewDeleteProfile);
        mTextViewLogout = (TextView) findViewById(R.id.textViewLogOut);
        mTextAddLocation = (TextView) findViewById(R.id.textaddlocation);


        email = getIntent().getStringExtra("EXTRA_email");


        mTextViewupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetting.this, EditProfile.class);
                intent.putExtra("EXTRA_USERNAME_UPDATE", intentusername);
                startActivity(intent);
            }
        });

        mTextViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetting.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mTextAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetting.this, Add_Location.class);
                startActivity(intent);
            }
        });



        mTextViewdelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetting.this, DeleteUser.class);
                //intent.putExtra("EXTRA_email_Delete", intentemail);
                intent.putExtra("EXTRA_USERNAME_ProfileSetting", intentusername);
                startActivity(intent);
            }
        });

    }


    }