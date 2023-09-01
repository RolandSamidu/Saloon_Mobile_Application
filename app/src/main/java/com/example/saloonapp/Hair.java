package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Hair extends AppCompatActivity {

    ImageView mTextViewfstraight,mTextViewfthareading,mTextViewfpiercing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

        mTextViewfstraight = (ImageView) findViewById(R.id.imageButtonstraighhair);
        mTextViewfthareading = (ImageView) findViewById(R.id.imageButtonthreading);
        mTextViewfpiercing = (ImageView) findViewById(R.id.imageButtonpiercing);

        mTextViewfstraight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Hair.this, Straighten_Hair.class);
                startActivity(registerIntent);
            }
        });
        mTextViewfthareading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Hair.this, Threading.class);
                startActivity(registerIntent);
            }
        });
        mTextViewfpiercing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Hair.this, Picrcing.class);
                startActivity(registerIntent);
            }
        });

    }
}