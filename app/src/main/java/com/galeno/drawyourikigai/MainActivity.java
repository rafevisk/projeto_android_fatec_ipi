package com.galeno.drawyourikigai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.galeno.drawyourikigai.AutoDraw.ActivityPaintFragment;
import com.galeno.drawyourikigai.AutoDraw.PaintActivity;
import com.galeno.drawyourikigai.InfoIkigai.AboutActivity;
import com.galeno.drawyourikigai.ManualDraw.PaintManualActivity;

public class MainActivity extends AppCompatActivity {
    Button btTip;
    Button btStart;
    Button btStartManual;
    private ActivityPaintFragment frag1 = new ActivityPaintFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btTip = (Button) findViewById(R.id.btTip);
        btStart = (Button) findViewById(R.id.btStart);
        btStartManual = (Button) findViewById(R.id.btStartManual);

        btTip.setOnClickListener((v) -> {
            Intent it = new Intent(this, AboutActivity.class);
            startActivity(it);
        });

        btStart.setOnClickListener((v) -> {
            //Intent it = new Intent(this, PaintActivity.class);
            //startActivity(it);
            Intent intent = new Intent(this, AutomaticDraw.class);
            startActivity(intent);
        });

        btStartManual.setOnClickListener((v) -> {
            Intent it = new Intent(this, PaintManualActivity.class);
            startActivity(it);
        });
    }
}
