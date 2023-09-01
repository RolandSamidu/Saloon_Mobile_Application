 package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Spinner;


import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 public class Apoinment<RequestQueue> extends AppCompatActivity implements OnItemSelectedListener {

    EditText mTextName;
    EditText mTextGender;
    EditText mTextSelectApoinment;
    EditText mTextTelephone;
    EditText RequestDate , txtTime;
    TextView mTextviewEmail;
    TextView Orderdate;
    Button mButtonSubmit;


     Spinner spinerapoinment;
     private Spinner selectionSpinner;
    ArrayList<String> salonapoinmentlist = new ArrayList<>();
    ArrayAdapter<String> salonapoinmentAdapter;
    RequestQueue requestQueue;

    String Email;




    LoginRegisterDb db;
    boolean alreadyRegister;
    String result = null;

    String name;
    String username;
    String selectemail;
    String gender;
    String selectapoinment;
    String telephone;
    String requestdate;
    String intentusername;

    String requestDate1,requestTime1,selectapoinment1,email;


     Spinner dynamicListSpinner;
     DatePickerDialog picker;
     TimePickerDialog picker2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoinment);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

       // requestdate = Volley.newRequestQueue(this);

        intentusername = getIntent().getStringExtra("EXTRA_USERNAME_HOME");
        //intentemail = getIntent().getStringExtra("EXTRA_email");

        mTextName = (EditText) findViewById(R.id.editTextapoinmentname);
        mTextGender = (EditText) findViewById(R.id.editTextAddress3);



        initDynamicListSpinner();
        initSelectionSpinner();

        ArrayList<String> listItems=new ArrayList<>();
        ArrayAdapter<String> adapter;

       spinerapoinment = (Spinner) findViewById(R.id.spMonths);
       spinerapoinment.setOnItemSelectedListener(this);
        //Spinner dynamicListSpinner = findViewById(R.id.spMonths);

        // spinner item select listener



        mTextTelephone = (EditText) findViewById(R.id.editTextTel);
        mButtonSubmit = (Button) findViewById(R.id.buttonContinue);
        mTextviewEmail = (TextView) findViewById(R.id.textViewEmail);
        mTextviewEmail.setText(Global.message);
        Orderdate =(TextView) findViewById(R.id.textViewDateDisplay);

        getDateTime();

        RequestDate = (EditText) findViewById(R.id.editTextDate);
        RequestDate.setInputType( InputType.TYPE_NULL);
        RequestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Apoinment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                RequestDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });




        // Get Current Time
        txtTime=(EditText)findViewById(R.id.in_time);
        txtTime.setInputType(InputType.TYPE_NULL);
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Apoinment.this,new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


                mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isConnected() == true) {

                            if(TextUtils.isEmpty(RequestDate.getText().toString().trim())|| TextUtils.isEmpty(mTextName.getText().toString().trim()) ||TextUtils.isEmpty(mTextGender.getText().toString().trim())||TextUtils.isEmpty(txtTime.getText().toString().trim())){
                                Toast.makeText(Apoinment.this,"Fill all Details", Toast.LENGTH_LONG).show();
                            }else{
                                insertAppoinment();
                                Intent intent1 = new Intent(Apoinment.this, Select_date.class);
                                intent1.putExtra("EXTRA_DATE", requestDate1);
                                intent1.putExtra("EXTRA_Time", requestTime1);
                                intent1.putExtra("EXTRA_Title", selectapoinment1);
                                intent1.putExtra("EXTRA_email", email);

                                System.out.println(email);
                                startActivity(intent1);
                                Toast.makeText(getApplicationContext(), "Apoinment Submited", Toast.LENGTH_SHORT).show();
                            }



                        } else {
                            Toast.makeText(Apoinment.this, "Check your Internet Connection!", Toast.LENGTH_LONG).show();
                        }

                    }


                });
            }

        private void getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());
        Orderdate.setText(currentDateandTime);



}

//-------------------------------
public void initDynamicListSpinner() {

   dynamicListSpinner = findViewById(R.id.spMonths);
    // Custom choices
    List<CharSequence> choices = new ArrayList<>();
    choices.add("Full Dressing");
    choices.add("Hair Strait");
    choices.add("Clean Up");
    choices.add("Waxing");
    choices.add("Threading");
    choices.add("Mancure");
    choices.add("Piercing");

    // Create an ArrayAdapter with custom choices
    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choices);

    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // Set the adapter to th spinner
    dynamicListSpinner.setAdapter(adapter);
}

    private void initSelectionSpinner() {
        selectionSpinner = findViewById(R.id.spMonths);

        // Set SpinnerActivity as the item selected listener
        selectionSpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(this, selectionSpinner.getSelectedItem() + " selected", Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }


     public void insertAppoinment() {
               final String name, gender, telephone,orderdate;

                name = mTextName.getText().toString().trim();
                gender = mTextGender.getText().toString().trim();
                selectapoinment1 = this.dynamicListSpinner.getSelectedItem().toString().trim();
                telephone = mTextTelephone.getText().toString().trim();
                requestDate1 = RequestDate.getText().toString().trim();
                email = mTextviewEmail.getText().toString().trim();
                orderdate = Orderdate.getText().toString().trim();
                requestTime1 = txtTime.getText().toString().trim();

             class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

                 @Override
                 protected String doInBackground(String... strings) {

                     ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                     nameValuePairs.add(new BasicNameValuePair("name", name));
                     nameValuePairs.add(new BasicNameValuePair("gender", gender));
                     nameValuePairs.add(new BasicNameValuePair("selectapoinment", selectapoinment1));
                     nameValuePairs.add(new BasicNameValuePair("telephone", telephone));
                     nameValuePairs.add(new BasicNameValuePair("requestdate", requestDate1));
                     nameValuePairs.add(new BasicNameValuePair("email", email));
                     nameValuePairs.add(new BasicNameValuePair("orderdate", orderdate));
                     nameValuePairs.add(new BasicNameValuePair("ordertime", requestTime1));



                     try {
                         HttpClient httpClient = new DefaultHttpClient();
                         String ServerURL = "http://nawagamuwadevalaya.com/SalonApoinment.php";
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
