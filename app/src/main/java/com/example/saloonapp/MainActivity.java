package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    LoginRegisterDb db;

    String result = null;
    boolean alreadyRegister;
    String username;
    String password;
   String success;
   String show;
   String email;
   String intentemail;






    private static final String URL_PRODUCTS = "https://www.nawagamuwadevalaya.com/SalonLogin.php";




//    Boolean CheckEditText ;
//    String HttpURL = "http://192.168.1.6/SalonLogin.php";
//    ProgressDialog progressDialog;
//    HashMap<String,String> hashMap = new HashMap<>();
//    HttpParse httpParse = new HttpParse();
//    public static final String UserEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        isConnected();
        db = new LoginRegisterDb(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        intentemail = getIntent().getStringExtra("Register_email");


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, Register.class);
                startActivity(registerIntent);


            }
        });


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUserDetails();
                ReadUser();

                //Toast.makeText(getApplicationContext(), "Login succesfully",Toast.LENGTH_SHORT).show();
            }
        });

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

    //Check user in a online database
    public void SelectUserDetails(){

       mTextPassword = (EditText) findViewById(R.id.edittext_password);
       mTextUsername = (EditText) findViewById(R.id.edittext_username);

        username = mTextUsername.getText().toString();
        password = mTextPassword.getText().toString();


        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {


                try {
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonLogin.php?username="+username +"&password="+password);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        System.out.println("result:" + line);
                    }
                    result = sb.toString();
                  //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                    Log.e("pass 2", "connection success");
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                }

               try {
                   if (username.equalsIgnoreCase(result)) {
                       success = "true";
                      Intent registerIntent = new Intent(MainActivity.this, LoginRegisterDb.Home.class);
                       //registerIntent.putExtra("EXTRA_USERNAME", username);
                       //registerIntent.putExtra("Email_home", "sachintha@gmail.com");
                       startActivity(registerIntent);
                       //Toast.makeText(MainActivity.this, "This is my Toast message!", Toast.LENGTH_LONG).show();
                   }

                   else
                       success = "false";
                       Toast.makeText(getApplicationContext(), "invalid username or password",Toast.LENGTH_SHORT).show();

                   //return success;

                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());

                }
               // return success;

                return success;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(success== "true"){
                    alreadyRegister = true;
                    Intent registerIntent = new Intent(MainActivity.this, LoginRegisterDb.Home.class);
                    //registerIntent.putExtra("EXTRA_USERNAME", username);
                    startActivity(registerIntent);
                    //Toast.makeText(MainActivity.this, "Login succesfully!", Toast.LENGTH_LONG).show();

                }else{
                    alreadyRegister = false;
                    //Toast.makeText(MainActivity.this, "Login fail!", Toast.LENGTH_LONG).show();



                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }



    //read sqlite database
    public void ReadUser(){

        username = mTextUsername.getText().toString().trim();
        password = mTextPassword.getText().toString().trim();


        Cursor res = db.checkUser(mTextUsername.getText().toString().trim());
        if(res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Please register first!", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            while (res.moveToNext()) {
                username = res.getString(3);
                password =  res.getString(6);
            }
            if(username.equals(username) && password.equals(password)){
                Toast.makeText(MainActivity.this, "Login successfull!", Toast.LENGTH_LONG).show();
                Global.username= mTextUsername.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, LoginRegisterDb.Home.class);
                intent.putExtra("EXTRA_USERNAME", username);
                startActivity(intent);
            }
            else
                Toast.makeText(MainActivity.this, "Invalid User details", Toast.LENGTH_LONG).show();
        }
    }
}
