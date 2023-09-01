package com.example.saloonapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class beauty_tips extends AppCompatActivity {

    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tips);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.txt_layout);

            viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper2);

            fade_in = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_in);
            fade_out = AnimationUtils.loadAnimation(this,
                    android.R.anim.fade_out);

            viewFlipper.setInAnimation(fade_in);
            viewFlipper.setOutAnimation(fade_out);

            //sets auto flipping
            viewFlipper.setAutoStart(true);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();

    }
}