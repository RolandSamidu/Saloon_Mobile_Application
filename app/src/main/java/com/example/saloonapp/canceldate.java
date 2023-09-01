package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class canceldate extends AppCompatActivity {

    String emailnewone;
    TextView mTextCancelRequest;
    int code;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceldate);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        emailnewone = getIntent().getStringExtra("EXTRA_email_home");
        mTextCancelRequest = (TextView) findViewById(R.id.textViewcancelreq);

        mTextCancelRequest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelRequest();
                Intent intent3 = new Intent(canceldate.this, LoginRegisterDb.Home.class);
                startActivity(intent3);
            }
        } );


      /*  mTextCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelRequest();
                Intent intent2 = new Intent(canceldate.this, LoginRegisterDb.Home.class);
                startActivity(intent2);
            }
        });*/
    }

    public void CancelRequest() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("cancelemail", emailnewone));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "http://nawagamuwadevalaya.com/SalonCancelAppoinment.php";
                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity entity = httpResponse.getEntity();

                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
                            Toast.LENGTH_LONG).show();
                }

                try {
                    URL url = new URL("http://nawagamuwadevalaya.com/SalonCancelAppoinment.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    result = sb.toString();
                    Log.e("pass 2", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                }

                try {
                    JSONObject json_data = new JSONObject(result);
                    code = (json_data.getInt("code"));
                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());

                }
                return "Data Delete Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                if (code == 1) {
                    Toast.makeText(canceldate.this, "Data Delete Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(canceldate.this, "Data Delete Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }



}