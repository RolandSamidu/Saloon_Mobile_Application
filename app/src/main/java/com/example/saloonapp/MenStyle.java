package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenStyle extends AppCompatActivity {

    ImageView mviewmencut,mviewmenbeard,mviewmencoloring,mviewmenhairrelaxing,mviewmenheadmassages,mviewmenhairperming,mviewmensetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_style);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        mviewmencut = (ImageView) findViewById(R.id.imageButtonwomen20);
        mviewmenbeard = (ImageView) findViewById(R.id.imageButtonthreading);
        mviewmencoloring = (ImageView) findViewById(R.id.imageButtonpiercing);
        mviewmenhairrelaxing = (ImageView) findViewById(R.id.imageButtonwomen4);
        mviewmenheadmassages = (ImageView) findViewById(R.id.imageButtonwomen5);
        mviewmenhairperming = (ImageView) findViewById(R.id.imageButtonwomen6);
        mviewmensetting = (ImageView) findViewById(R.id.imageButtonwomen7);



        mviewmencut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent1);
            }
        } );

        mviewmenbeard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent2);
            }
        } );
        mviewmencoloring.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent3);
            }
        } );
        mviewmenhairrelaxing.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent4);
            }
        } );
        mviewmenheadmassages.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent5);
            }
        } );
        mviewmenhairperming.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent6);
            }
        } );
        mviewmensetting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(MenStyle.this, Men_Cut.class);
                startActivity(intent7);
            }
        } );


    }
}