package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class hair_beauty_bridle extends AppCompatActivity {


    ImageView img1;
    String mbuttonhair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_beauty_bridle);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        mbuttonhair = getIntent().getStringExtra("EXTRA_Bhair");

        img1 = (ImageView)findViewById(R.id.imageButtonwomen20);

        img1.setVisibility(View.INVISIBLE);


    }
}