package com.galeno.drawyourikigai;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class PaintManualActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide(); //hide toolbar padrao
        setContentView(R.layout.activity_paint_manual);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getString(R.string.title_manual));

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation((ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));

        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android)
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }*/
}
