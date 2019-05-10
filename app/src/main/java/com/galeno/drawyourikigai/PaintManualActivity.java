package com.galeno.drawyourikigai;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

public class PaintManualActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide(); //hide toolbar padrao
        setContentView(R.layout.activity_paint_manual);
        Toolbar toolbar = findViewById(R.id.toolBar);
        //setSupportActionBar(toolbar);

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation((ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));

        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
