package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.CalendarView;
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


public class Select_date<CalenderView> extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView currentDate;
    private Button btnsavenote;
    int code;
    String result=null;

    private EditText newNote, showNote;
    //String selectedDate = "10/11/2021";
    String requestdate,requesttime,requesttitle,appoinmentid,email;

    Button mbuttonexit;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        calendarView = (CalendarView) findViewById(R.id.calendarView1);
        mbuttonexit = (Button) findViewById(R.id.buttonexit);




        // currentDate = (TextView) findViewById(R.id.calendarView);
        //  btnsavenote = (Button) findViewById(R.id.save_note);
        //  newNote = (EditText) findViewById(R.id.newNote);
        // showNote = (EditText) findViewById(R.id.shownote);
        // final String [][] array = new String[31][12];
        requestdate = getIntent().getStringExtra("EXTRA_DATE");
        requesttime = getIntent().getStringExtra("EXTRA_Time");
        requesttitle = getIntent().getStringExtra("EXTRA_Title");
        email = getIntent().getStringExtra("EXTRA_email");


        mbuttonexit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Select_date.this, LoginRegisterDb.Home.class);
                intent1.putExtra("EXTRA_email_selectdate", email);
                startActivity(intent1);
            }
        } );





        String parts[] = requestdate.split("/");




        int day1 = Integer.parseInt(parts[2]);
        int month1 = Integer.parseInt(parts[1]);
        int year1 = Integer.parseInt(parts[0]);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year1);
        calendar.set(Calendar.MONTH + 1, month1);
        calendar.set(Calendar.DAY_OF_MONTH, day1);

        final long  milliTime= calendar.getTimeInMillis();
        calendarView.setDate (milliTime, true, true);

        //  calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime());

        // Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        //   Toast.makeText(getApplicationContext(), "" + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                Toast.makeText(getApplicationContext(),"Hello click listner",Toast.LENGTH_SHORT).show();
                Toast.makeText(Select_date.this, "Hello click listner11!", Toast.LENGTH_LONG).show();

                    Calendar calendarEvent = Calendar.getInstance();
                    Intent i = new Intent(Intent.ACTION_EDIT);
                    i.setType("vnd.android.cursor.item/event");
                    i.putExtra("beginTime", milliTime);
                    i.putExtra("allDay", true);
                    i.putExtra("rule", 0);  // "FREQ=YEARLY"
                    i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
                    i.putExtra("title", requesttitle);
                    i.putExtra("description","KONDE KAPPANNA");
                    i.putExtra("eventLocation","180/5, Hinatikumbura road, Koswatta");


                startActivity(i);




            }
            });


    /*    mbuttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected() == true){
                    CancelRequest();
                }else{
                    Toast.makeText(Select_date.this,"Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });*/


    }


/*
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
              //  String tDate;
              //  tDate = (dayOfMonth) + "/" + month + "/" + year;
               // calendarView.setDate(System.currentTimeMillis(),false,true);


                String parts[] = requestdate1.split("/");


                int year1 = Integer.parseInt(parts[0]);
                int month1 = Integer.parseInt(parts[1]);
                int day1 = Integer.parseInt(parts[2]);


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year1);
                calendar.set(Calendar.MONTH + 1, month1);
                calendar.set(Calendar.DAY_OF_MONTH, day1);

                long milliTime = calendar.getTimeInMillis();
               calendarView.setDate (milliTime, true, true);

                //  calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime());
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getApplicationContext(), "" + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }

           *//* public void setDateTime(CalendarView view,int year,int month, int dayOfMonth){
                calendarView.setDate(System.currentTimeMillis(),false,true);
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
            }*//*
        });
    }*/

  /*  //Delete user from online database
public void CancelRequest(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("cancelemail",email));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String ServerURL = "http://nawagamuwadevalaya.com/SalonCancelAppoinment.php";
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
                    URL url = new URL("http://nawagamuwadevalaya.com/SalonCancelAppoinment.php");
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
                    Toast.makeText(Select_date.this, "Data Delete Successful!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Select_date.this, "Data Delete Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
*/

    //check Internet connection
    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // Toast.makeText(SignUp.this,"Internet Successful!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // Toast.makeText(SignUp.this,"Internet Fail!", Toast.LENGTH_LONG).show();
            return false;

        }
    }

        //side menu icon
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_setdate, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            try {
                switch (item.getItemId()) {
                    case R.id.home2:
                        try {
                            Intent intent1 = new Intent(Select_date.this, canceldate.class);
                            intent1.putExtra("EXTRA_email_home", email);
                            startActivity(intent1);
                            return true;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    default:
                        return super.onOptionsItemSelected(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


}
