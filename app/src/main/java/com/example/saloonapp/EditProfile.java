package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    EditText mTextId;
    EditText mTextName;
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPhoneNumber;
    EditText mTextPassword;
    Button mButtonUpdate;
    ImageView mimageView;
    int code;
    int position = 0;

    LoginRegisterDb db;
    boolean alreadyRegister;
    String result = null;
    String id,name,username,email,phonenumber,success;

    String intentusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

          intentusername = Global.username;
          mTextId = (EditText) findViewById(R.id.edittext_id);
          mTextName = (EditText) findViewById(R.id.edittext_name);
          mTextUsername = (EditText) findViewById(R.id.edittext_username);
          mTextEmail = (EditText) findViewById(R.id.edittext_email);
          mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phonenumber);
          //mTextPassword = (EditText) findViewById(R.id.edittext_password);
          mButtonUpdate = (Button) findViewById(R.id.button_register);
          mimageView = (ImageView) findViewById(R.id.imageView55);






        if(intentusername.isEmpty()){
            Toast.makeText(EditProfile.this,"Username is Empty", Toast.LENGTH_LONG).show();
        }else{
            SelectUserDetails();
        }
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected() == true){
                    UpdateUserDetails();

                        if (Global.username.equalsIgnoreCase(username)) {
                            success = "true";

                        Intent intent1 = new Intent(EditProfile.this, LoginRegisterDb.Home.class);
                        intent1.putExtra("EXTRA_USERNAME_UPDATE", email);
                        //Toast.makeText(EditProfile.this,"Update succefully!", Toast.LENGTH_LONG).show();
                        startActivity(intent1);
                    }
                    else {
                            success = "false";
                            Intent intent2 = new Intent(EditProfile.this, MainActivity.class);
                            Toast.makeText(EditProfile.this, "User Name Changed, Please Log again!", Toast.LENGTH_LONG).show();
                            startActivity(intent2);
                        }



                }else{
                    Toast.makeText(EditProfile.this,"Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }

            }
        });


}

    //read data from online database
    public void SelectUserDetails(){

        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            String id, name, username,email,phonenumber;
            String ppicture;

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonSelectUser.php?username="+ intentusername);
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
                    id = (json_data.getString("Id"));
                    name = (json_data.getString("Name"));
                    username = (json_data.getString("Username"));
                    email = (json_data.getString("Email"));
                    phonenumber = (json_data.getString("Phonenumber"));
                   //password = (json_data.getString("Password"));
                    ppicture = (json_data.getString("Image"));
//------------------------------------


                    //---------------------------------------------------

                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());

                }
                return "Data Select Successfully";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mTextId.setText(id);
                mTextName.setText(name);
                mTextUsername.setText(username);
                mTextEmail.setText(email);
                mTextPhoneNumber.setText(phonenumber);
               // mTextPassword.setText(password);
                // byte[] image = res.getBlob(6);

                //-----------------------------------------------------------------
                byte[] rawImage = Base64.decode(ppicture , Base64.DEFAULT);
               // Bitmap bmp = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);
                ByteArrayInputStream imageStream = new ByteArrayInputStream(rawImage);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                //mimageView.setImageBitmap(bitmap);
                //mimageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 250, 250, false));

                mimageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 250, 250, false));

                //-----------------------------------------------------------------
                //Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
               // mimageView.setImageBitmap(bmp);


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

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


    //update online database
    public void UpdateUserDetails(){

        id = mTextId.getText().toString().trim();
        name =   mTextName.getText().toString().trim();
        username =   mTextUsername.getText().toString().trim();
        email =   mTextEmail.getText().toString().trim();
        phonenumber =   mTextPhoneNumber.getText().toString().trim();


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id",id));
                nameValuePairs.add(new BasicNameValuePair("name",name));
                nameValuePairs.add(new BasicNameValuePair("username",username));
                nameValuePairs.add(new BasicNameValuePair("email",email));
                nameValuePairs.add(new BasicNameValuePair("phonenumber",phonenumber));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "https://www.nawagamuwadevalaya.com/SalonUpdateUser.php";
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
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonUpdateUser.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                 //   try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                        String line = null;
                        StringBuilder sb = new StringBuilder();
                     //  } finally {
                    //    urlConnection.disconnect();
                    //}


                    while ((line = reader.readLine()) != null)
                    {
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
                    code =(json_data.getInt("code"));
                }
                catch(Exception e) {
                    Log.e("Fail 3", e.toString());

                }


                return "Data Update Successfully";


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                if(code ==0)
                {
                    Toast.makeText(EditProfile.this, "Data Update fail!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EditProfile.this, "Data Update Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


    }