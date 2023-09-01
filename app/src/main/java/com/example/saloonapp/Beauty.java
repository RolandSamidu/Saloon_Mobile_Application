package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Beauty extends AppCompatActivity {

    ImageView mTextViewfcleanup,mTextViewfwaxing,mTextViewfmanicure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        mTextViewfcleanup = (ImageView) findViewById(R.id.imageButtoncleanup);
        mTextViewfwaxing = (ImageView) findViewById(R.id.imageButtonwaxing);
        mTextViewfmanicure = (ImageView) findViewById(R.id.imageButtonmaincure);

        mTextViewfcleanup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Beauty.this, CleanUp.class);
                startActivity(registerIntent);
            }
        });
        mTextViewfwaxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Beauty.this, Waxing.class);
                startActivity(registerIntent);
            }
        });
        mTextViewfmanicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Beauty.this, Manicure.class);
                startActivity(registerIntent);
            }
        });
    }
}