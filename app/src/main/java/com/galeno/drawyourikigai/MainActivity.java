package com.galeno.drawyourikigai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btTip;
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btTip = (Button) findViewById(R.id.btTip);
        btStart = (Button) findViewById(R.id.btStart);

        btTip.setOnClickListener((v) -> {
            Intent it = new Intent(this, AboutActivity.class);
            startActivity(it);
        });

        btStart.setOnClickListener((v) -> {
            Intent it = new Intent(this, PaintActivity.class);
            startActivity(it);
        });
    }
}