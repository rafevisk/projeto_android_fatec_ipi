package com.galeno.drawyourikigai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class AutomaticDraw extends AppCompatActivity {
    final String TAG = "Teste";
    int colorc1 = setColorPaint();
    int colorc2 = setColorPaint();
    int colorc3 = setColorPaint();
    int colorc4 = setColorPaint();

    int circulodavez;

    String q1 = "";
    String q2 = "";
    String q3 = "";
    String q4 = "";

    String passion = "";
    String mission = "";
    String profession = "";
    String vocation = "";

    ArrayList<String> love;
    ArrayList<String> good;
    ArrayList<String> need;
    ArrayList<String> paid;

    ArrayList<String> pas;
    ArrayList<String> mis;
    ArrayList<String> prof;
    ArrayList<String> voca;


    Bitmap tempBmp = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
    Canvas c = new Canvas();
    Paint paint = new Paint();

    boolean redesenhar = false;
    AutoCompleteTextView input;

    //EditText input;

    public void white(final String pergunta, final int circulo, ArrayList base) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(pergunta);



        input = new AutoCompleteTextView(this);
        builder.setView(input);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item, base);
        input.setThreshold(1); //will start working from first character
        input.setAdapter(adapter);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String txt = input.getText().toString();
                if (!txt.isEmpty()) {
                    if (circulo == 1) {
                        q1 = txt;
                        addData("love",q1);
                    }
                    if (circulo == 2) {
                        q2 = txt;
                        addData("good_at",q2);
                    }
                    if (circulo == 3) {
                        q3 = txt;
                        addData("need",q3);
                    }
                    if (circulo == 4) {
                        q4 = txt;
                        addData("paid_for",q4);
                    }
                    if (circulo == 5) {
                        passion = txt;
                        addData("passion",passion);
                    }
                    if (circulo == 6) {
                        mission = txt;
                        addData("mission",mission);
                    }
                    if (circulo == 7) {
                        profession = txt;
                        addData("profession",profession);
                    }
                    if (circulo == 8) {
                        vocation = txt;
                        addData("vocation",vocation);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.messagem_warning, Toast.LENGTH_SHORT).show();
                }

            }

        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    //Save data on FireBase
    public void addData(String nameCircle, String txt){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(nameCircle).push().setValue(txt);

    }

    //Search data on FireBase
    public ArrayList<String> listAllData(String nameCircle){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(nameCircle);

        ArrayList<String> dados = new ArrayList<String>();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Read all child
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String value = child.getValue(String.class);
                    dados.add(value);
                    //Log.d(TAG, "Value is: " + value);
                }
                Set<String> dadosSemRepeticoes = new HashSet<>(dados);
                //Iterator<String> iteradorDadosSemRepeticoes = dadosSemRepeticoes.iterator();
                dados.clear();
                for ( String item : dadosSemRepeticoes){
                    dados.add(item);
                }
/*                while (iteradorDadosSemRepeticoes.hasNext()) {
                    dados.add(iteradorDadosSemRepeticoes.next());
                    Log.d(TAG, "Value is: " + iteradorDadosSemRepeticoes.next());
                }*/
                //return dados;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return dados;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicia firebase
        FirebaseApp.initializeApp(this);




        class VennView extends View {
            public VennView(Context context) {
                super(context);

            }

            void update(Canvas canvas) {

                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);

                float w = canvas.getWidth();
                float h = canvas.getHeight();
                float cx = w / 2f;
                float cy = h / 2;
                float r = w / 5;
                float tx = (float) (r * Math.cos(15 * Math.PI / 180));
                float ty = (float) (r * Math.sin(15 * Math.PI / 180));
                float expand = 1.5f;
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

                //Escrevendo respostas
                paint.setColor(Color.argb(255, 0, 0, 0));
                paint.setTextSize(26);
                c.drawText("" + q1, cx - tx + 25, 340, paint);
                c.drawText("" + q2, cx / 14, cy + ty + 30, paint);
                c.drawText("" + q3, cx + tx + 84, cy + ty + 30, paint);
                c.drawText("" + q4, cx - tx + 75, cy + ty * 8 + 30, paint);


                c.drawText("" + passion, cx - tx - 30, cy - tx + 50, paint);
                c.drawText("" + mission, cx + tx - 60, cy - tx + 50, paint);
                c.drawText("" + profession, cx - tx - 70, cy + tx + 20, paint);
                c.drawText("" + vocation, cx + tx - 60, cy + tx + 20, paint);

                //atualizando
                postInvalidate();

            }

            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                tempBmp.recycle();
                tempBmp = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);//ARGB_8888
                c.setBitmap(tempBmp);

                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);

                float w = canvas.getWidth();
                float h = canvas.getHeight();
                float cx = w / 2f;
                float cy = h / 2;
                float r = w / 5;
                float tx = (float) (r * Math.cos(15 * Math.PI / 180));
                float ty = (float) (r * Math.sin(15 * Math.PI / 180));
                float expand = 1.5f;

                paint.setAntiAlias(true);

                PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;
                paint.setXfermode(new PorterDuffXfermode(mode));

                Resources res = getResources();

                //1
                paint.setColor(colorc1);//t
                c.drawCircle(cx, cy - r - 10, expand * r, paint);

                paint.setColor(colorc2);
                c.drawCircle(cx - tx, cy + ty - 20, expand * r, paint);

                paint.setColor(colorc3);
                c.drawCircle(cx + tx, cy + ty - 20, expand * r, paint);

                paint.setColor(colorc4);

                c.drawCircle(cx, cy + ty * 4, expand * r, paint);


                canvas.drawBitmap(tempBmp, 0, 0, null);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

                paint.setColor(Color.BLACK);
                paint.setTextSize(80);
                c.drawText("Ikigai", cx - tx + 42, 52 * h / 100, paint);


                paint.setTextSize(25);

                c.drawText(getString(R.string.t_love), cx - tx + 40, cy - tx * 2 - 20, paint);
                c.drawText(getString(R.string.love), cx - tx + 110, cy - tx * 2 + 30, paint);

                c.drawText(getString(R.string.t_good), cx / 14, cy + ty - 40, paint);
                c.drawText(getString(R.string.good), cx / 14, cy + ty - 10, paint);

                c.drawText(getString(R.string.t_need), cx + tx + 30, cy + ty - 30, paint);
                c.drawText(getString(R.string.need), cx + tx + 84, cy + ty, paint);

                c.drawText(getString(R.string.t_paid), cx - tx + 25, cy + ty * 7, paint);
                c.drawText(getString(R.string.paid), cx - tx + 75, cy + ty * 8, paint);

                paint.setTextSize(24);
                c.drawText(getString(R.string.passion), cx - tx - 30, cy - tx + 20, paint);
                c.drawText(getString(R.string.mission), cx + tx - 60, cy - tx + 20, paint);
                c.drawText(getString(R.string.profession), cx - tx - 70, cy + tx - 20, paint);
                c.drawText(getString(R.string.vocation), cx + tx - 60, cy + tx - 20, paint);

                if (redesenhar == true) {
                    update(c);
                }

            }

            @Override
            public boolean onTouchEvent(final MotionEvent event) {

                float w = c.getWidth();
                float h = c.getHeight();
                float cx = w / 2f;
                float cy = h / 2;
                float r = w / 5;
                float tx = (float) (r * Math.cos(15 * Math.PI / 180));
                float ty = (float) (r * Math.sin(15 * Math.PI / 180));
                float expand = 1.5f;

                boolean c1 = false;
                boolean c2 = false;
                boolean c3 = false;
                boolean c4 = false;
                boolean cum = false;

                Canvas canvas = new Canvas();

                final float x = event.getX();
                final float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Capturando o toque do circulo 1
                        if (y > cy - r - 10 - expand * r && y < cy - r - 10 + expand * r && x > cx - expand * r && x < cx + expand * r) {
                            cum = true;
                        } else {
                            cum = false;
                        }

                        // Capturando o toque do circulo 2
                        if (y > cy + ty - 20 - expand * r && y < cy + ty - 20 + expand * r && x > cx - tx - expand * r && x < cx - tx + expand * r) {
                            c2 = true;
                        } else {
                            c2 = false;
                        }

                        // Capturando o toque do circulo 3
                        if (y > cy + ty - 20 - expand * r && y < cy + ty - 20 + expand * r && x > cx + tx - expand * r && x < cx + tx + expand * r) {
                            c3 = true;
                        } else {
                            c3 = false;
                        }

                        // Capturando o toque do circulo 4
                        if (y > cy + ty * 4 - expand * r && y < cy + ty * 4 + expand * r && x > cx - expand * r && x < cx + expand * r) {
                            c4 = true;
                        } else {
                            c4 = false;
                        }

                        if (c1 = true && c2 == true && c3 == true && c4 == true) {
                            Toast.makeText(getApplicationContext(), "Ikigai", Toast.LENGTH_LONG).show();
                        }

                        if (cum == true && c2 == false && c3 == false && c4 == false) {
                            circulodavez = 1;
                            //Inicia os arraylist com os dados
                            love = listAllData("love");
                            white(getString(R.string.t_love) + "?", 1,love);
                        }
                        if (c2 == true && cum == false && c3 == false && c4 == false) {
                            circulodavez = 2;
                            good = listAllData("good_at");
                            white(getString(R.string.t_good) + " " + getString(R.string.good) + "?", 2,good);
                        }
                        if (c3 == true && c2 == false && cum == false && c4 == false) {
                            circulodavez = 3;
                            need = listAllData("need");
                            white(getString(R.string.t_need) + " " + getString(R.string.need) + "?", 3,need);
                        }

                        if (c4 == true && c2 == false && c3 == false && cum == false) {
                            circulodavez = 4;
                            paid = listAllData("paid_for");
                            white(getString(R.string.t_paid) + " " + getString(R.string.paid) + "?", 4,paid);
                        }

                        if (cum == true && c2 == true && c3 == false && c4 == false) {

                            pas = listAllData("passion");

                            white("Your " + getString(R.string.passion), 5,pas);
                        }

                        if (cum == true && c2 == false && c3 == true && c4 == false) {
                            mis = listAllData("mission");

                            white("Your " + getString(R.string.mission), 6,mis);
                        }

                        if (c1 == false && c2 == true && c3 == false && c4 == true) {
                            prof = listAllData("profession");

                            white("Your " + getString(R.string.profession), 7,prof);
                        }

                        if (c1 == false && c2 == false && c3 == true && c4 == true) {
                            voca = listAllData("vocation");
                            white("Your " + getString(R.string.vocation), 8,voca);
                        }

                        update(c);
                        draw(c);
                        redesenhar = true;

                        break;
                    default:
                        break;
                }
                return false;
            }
        }
        this.setContentView(new VennView(this));
    }
    public int setColorPaint(){
        Random random = new Random ();
        int r = random.nextInt(155)+100;
        int g = random.nextInt(155)+100;
        int b = random.nextInt(155)+100;
        int color = Color.argb(255, r, g, b);
        return color;
    }
}