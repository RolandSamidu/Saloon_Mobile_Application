package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DeleteUser extends AppCompatActivity {

    //EditText emailEditText;
    Button deleteButton;
    EditText usernameEditText;
    //String intentemail;
    String intenusername;

    String result=null;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        //intentemail = getIntent().getStringExtra("EXTRA_email_Delete");
        intenusername = getIntent().getStringExtra("EXTRA_USERNAME_ProfileSetting");
        deleteButton = (Button) findViewById(R.id.buttondeactivate);

       //emailEditText = (EditText) findViewById(R.id.edittext_email);
         usernameEditText = (EditText) findViewById(R.id.edittext_email);

       //emailEditText.setText(intentemail);
        usernameEditText.setText(intenusername);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected() == true){
                    DeleteUserDetails();
                    Intent intent1 = new Intent(DeleteUser.this, MainActivity.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(DeleteUser.this,"Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Delete user from online database
    public void DeleteUserDetails(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username",intenusername));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "https://www.nawagamuwadevalaya.com/SalonDeleteUser.php";
                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity entity = httpResponse.getEntity();

                }
                catch(Exception e) {
                    Log.e("Fail 1", e.toString());
                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
                            Toast.LENGTH_LONG).show();
                }

                try {
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonDeleteUser.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    result = sb.toString();
                    Log.e("pass 2", "connection success ");
                }
                catch(Exception e) {
                    Log.e("Fail 2", e.toString());
                }

                try {
                    JSONObject json_data = new JSONObject(result);
                    code=(json_data.getInt("code"));
                }
                catch(Exception e) {
                    Log.e("Fail 3", e.toString());

                }
                return "Data Delete Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                if(code==1) {
                    Toast.makeText(DeleteUser.this, "Data Delete Successful!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DeleteUser.this, "Data Delete Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    //check Internet connection
    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // Toast.makeText(SignUp.this,"Internet Successful!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // Toast.makeText(SignUp.this,"Internet Fail!", Toast.LENGTH_LONG).show();
            return false;

        }
    }

}