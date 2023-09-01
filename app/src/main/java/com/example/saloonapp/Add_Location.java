package com.example.saloonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

public class Add_Location extends AppCompatActivity {

    Button Addlocation;
    EditText locationEditText,v1EditText,v2EditText;
    String location,v1,v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__location);

        Addlocation = (Button) findViewById(R.id.buttonaddlocation);
        locationEditText = (EditText) findViewById(R.id.add_location2);
        v1EditText = (EditText) findViewById(R.id.add_location5);
        v2EditText = (EditText) findViewById(R.id.add_location8);




        Addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    InsertLocationDetails();
                    Toast.makeText(getApplicationContext(), "Location add succesfully",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Add_Location.this, "Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    //insert data to the online database

    public void InsertLocationDetails() {


        location = locationEditText.getText().toString().trim();
        v1 = v1EditText.getText().toString().trim();
        v2 = v2EditText.getText().toString().trim();

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("v1", v1));
                nameValuePairs.add(new BasicNameValuePair("v2", v2));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "https://www.nawagamuwadevalaya.com/SalonAddLocation.php";
                    HttpPost httpPost = new HttpPost(ServerURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity entity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //Toast.makeText(Register.this, "Data Insert Successfully", Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    //cheack internet connection
    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            //Toast.makeText(Register.this,"Internet Successful!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            //Toast.makeText(Register.this,"Internet Fail!", Toast.LENGTH_LONG).show();
            return false;

        }
    }
}