package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WomenStyle extends AppCompatActivity {


    ImageView mviewwomenfulldress,mviewwomenhairstait,mviewmomencleanup,mviewomenwaxing,mviewomentheading,mviewomenmancure,mviewomenpricing;



    String mbuttonhair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_style);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);




        mviewwomenfulldress = (ImageView) findViewById(R.id.imageButtonthreading);
        mviewwomenhairstait = (ImageView) findViewById(R.id.imageButtonpiercing);
        mviewmomencleanup = (ImageView) findViewById(R.id.imageButtonwomen20);
        mviewomenwaxing = (ImageView) findViewById(R.id.imageButtonwomen30);
        mviewomentheading = (ImageView) findViewById(R.id.imageButtonwomen31);
        mviewomenmancure = (ImageView) findViewById(R.id.imageButtonwomen32);
        mviewomenpricing = (ImageView) findViewById(R.id.imageButtonwomen33);


        
        mviewwomenfulldress.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(WomenStyle.this, Full_dressing.class);
                startActivity(intent11);
            }
        } );

        mviewwomenhairstait.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(WomenStyle.this, Straighten_Hair.class);
                startActivity(intent12);
            }
        } );
        mviewmomencleanup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent13 = new Intent(WomenStyle.this, CleanUp.class);
                startActivity(intent13);
            }
        } );

        mviewomenpricing.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent17 = new Intent(WomenStyle.this, Picrcing.class);
                startActivity(intent17);
            }
        } );



        mviewomenwaxing.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent14 = new Intent(WomenStyle.this, Manicure.class);
                startActivity(intent14);
            }
        } );
        mviewomentheading.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent15 = new Intent(WomenStyle.this, Waxing.class);
                startActivity(intent15);
            }
        } );
        mviewomenmancure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent16 = new Intent(WomenStyle.this, Threading.class);
                startActivity(intent16);
            }
        } );



    }
}