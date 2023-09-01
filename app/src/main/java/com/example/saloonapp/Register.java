package com.example.saloonapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText mTextName;
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPhoneNumber;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    ImageView mImageView;
    Button mButtonChoose;

    LoginRegisterDb db;
    boolean alreadyRegister;
    String result = null;

    String name;
    String username;
    String email;
    String phonenumber;
    String password;
    String cfpassword;

    Bitmap bitmap;
    byte img[];

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_PICK_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        db = new LoginRegisterDb(this);
        mTextName = (EditText) findViewById(R.id.edittext_name);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phonenumber);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirmpassword);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        mImageView = findViewById(R.id.imageView);
        mButtonChoose = findViewById(R.id.button_choose_image);

        File imgFile = new  File("/sdcard/Images/test_image.jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageView);

            myImage.setImageBitmap(myBitmap);

        }


        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    SelectUserDetails();
                    insertUser();
                    InsertUserDetails();




                  Global.message = mTextEmail.getText().toString();
                  Intent intent1 = new Intent(Register.this, MainActivity.class);
                   // intent1.putExtra("Register_email", "sachintha@gmail.com");
                    startActivity(intent1);
                    Toast.makeText(getApplicationContext(), "registerd succesfully",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Register.this, "Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }

            }
        });

        //handle button click (upload)
        mButtonChoose.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                //chech runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        //Permision not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_PICK_CODE);
                    } else {

                        //permission already garnted
                        pickImageFromGallery();

                    }
                } else {

                    //system os is less then marshmallow
                    pickImageFromGallery();

                }
            }


        });
    }

    //mysql data base
    public void insertUser() {
        name = mTextName.getText().toString().trim();
        username = mTextUsername.getText().toString().trim();
        email = mTextEmail.getText().toString().trim();
        phonenumber = mTextPhoneNumber.getText().toString().trim();
        password = mTextPassword.getText().toString().trim();
        cfpassword = mTextConfirmPassword.getText().toString().trim();
        //byte[] image =imageViewToByte(mImageView);
        //mImageView.setImageResource(R.mipmap.ic_launcher);

                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                //img = stream.toByteArray();

                //mImageView.setImageBitmap(bitmap);
        //-----------------------------------------
        //String image = mImageView.getImageMatrix().toString().trim();



        if (TextUtils.isEmpty(username)) {
            mTextUsername.setError("Email is Required");
        } else if (TextUtils.isEmpty(password)) {
            mTextPassword.setError("Password is Required");
        } else {
            if (alreadyRegister == false) {

                if (password.equals(cfpassword) == true) {
                    //long val = db.addUser(name,user,email,phonenumber,pwd,image);
                    boolean isInserted = db.insertUser(name, username, email, phonenumber, password, cfpassword);
                    //InsertUserDetails();

                    if (isInserted == true) {
                        Toast.makeText(Register.this, "Data Insert Successful!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.putExtra("EXTRA_EMAIL_HOMEPAGE", phonenumber);
                        startActivity(intent);
                    } else {
                       // Toast.makeText(Register.this, "Data Insert Fail!", Toast.LENGTH_LONG).show();
                    }
                } else {
                   // Toast.makeText(Register.this, "Password not match!", Toast.LENGTH_LONG).show();
                }
            } else {
                //Toast.makeText(Register.this, "User Already Registered", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Check user in a online database
    public void SelectUserDetails() {

        username = null;
        password = null;

        username = mTextUsername.getText().toString().trim();

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonRegister.php?username=" + username);
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
                    username = (json_data.getString("username"));
                    password = (json_data.getString("password"));
                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());

                }
                return "Data Select Successfully";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (TextUtils.isEmpty(username)) {
                    alreadyRegister = true;
                } else {
                    alreadyRegister = false;
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

   /* //Validation
    public boolean passwordVaildation() {
        CharSequence password = mTextPassword.getText().toString().trim();
        Pattern passwordPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        Matcher passwordMatcher = passwordPattern.matcher(password);

        //Password Validation
        if (passwordMatcher.matches()) {
            return true;
        } else {
            mTextPassword.setError("Password not match");
            return false;
        }
    }

    public boolean emailVaildation() {
        CharSequence email = mTextEmail.getText().toString().trim();
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher emailMatcher = emailPattern.matcher(email);

        //Email Validation
        if (emailMatcher.matches()) {
            return true;
        } else {
            mTextEmail.setError("E-mail not match");
            return false;
        }
    }
*/

    //insert data to the online database

    public void InsertUserDetails() {
        final String name, username, email, phonenumber, password, cfpassword, imagedata;

        name = mTextName.getText().toString().trim();
        username = mTextUsername.getText().toString().trim();
        email = mTextEmail.getText().toString().trim();
        phonenumber = mTextPhoneNumber.getText().toString().trim();
        password = mTextPassword.getText().toString().trim();
        cfpassword = mTextConfirmPassword.getText().toString().trim();


        BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap converetdImage = getResizedBitmap(bitmap, 500);
        imagedata = getStringImage(converetdImage);


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("phonenumber", phonenumber));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("cpassword", cfpassword));
                nameValuePairs.add(new BasicNameValuePair("profilepic", imagedata.toString()));

                //  insertUser();


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "https://www.nawagamuwadevalaya.com/SalonRegister.php";
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


  private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of picked image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_PICK_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permition was granted
                    pickImageFromGallery();
                }
                else {
                    //permission wasd denied
                    Toast.makeText(this, "Permission denied....!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
/*    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }*/

    public String getStringImage(Bitmap bmp) {
       // Bitmap original = BitmapFactory.decodeStream(getAssets().open("1024x768.jpg"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100 , baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            /* Bitmap bitmapImage = BitmapFactory.decodeFile("/sdcard/Images/test_image.jpg");
             int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
             Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
             mImageView.setImageBitmap(scaled);*/
            //set image to image view
            mImageView.setImageURI(data.getData());
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
/*
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

             Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                mImageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register.this,"Image is Uploading","Please Wait",false,false);
            }*/

}


