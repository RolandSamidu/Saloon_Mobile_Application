package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Contact_Us extends AppCompatActivity {

    TextView mlocation1;
    TextView mlocation2;
    String intentlocation,intentlocation1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        mlocation1 = (TextView) findViewById(R.id.textView5);
        mlocation2 = (TextView) findViewById(R.id.textView5);

        //intentlocation1 = getIntent().getStringExtra("INTENT_LOCATION");

        mlocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Contact_Us.this, MapsActivity.class);
                registerIntent.putExtra("INTENT_LOCATION", intentlocation1);
                startActivity(registerIntent);


            }
        });


        mlocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Contact_Us.this, MapsActivity.class);
               //registerIntent.putExtra("INTENT_LOCATION", intentlocation1);
                startActivity(registerIntent);


            }
        });

    }


}