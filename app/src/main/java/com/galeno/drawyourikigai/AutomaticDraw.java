package com.galeno.drawyourikigai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AutomaticDraw extends AppCompatActivity {

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

    Path path = new Path();

    Bitmap tempBmp = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
    Canvas c = new Canvas();
    Canvas aaa = new Canvas();
    Paint paint = new Paint();
    Paint p = new Paint();
    String texto;
    boolean redesenhar = false;

    EditText input;
    Button shoBtn;


    public void inpurDialog(final int circulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("What do you want to do ?");

        builder.setPositiveButton("Write", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                if (circulo == 1) {
                    white("Whats you Love ?", 1);
                }
                if (circulo == 2) {
                    white("Whats you are Gooad At ?", 2);
                }
                if (circulo == 3) {
                    white("Whats you World Need ?", 3);
                }
                if (circulo == 4) {
                    white("Whats you Can Be Paid For ?", 4);
                }


            }

        });

        builder.setNeutralButton("Change color", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                //openColorPicker();

            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }


    public void white(final String pergunta, final int circulo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(pergunta);

        input = new EditText(this);
        builder.setView(input);
        //Salvar aqui no firebase
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String txt = input.getText().toString();


                if (circulo == 1) {
                    q1 = txt;
                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");

                    myRef.setValue("Hello, World!");
                }
                if (circulo == 2) {
                    q2 = txt;
                }
                if (circulo == 3) {
                    q3 = txt;
                }
                if (circulo == 4) {
                    q4 = txt;
                }
                if (circulo == 5) {
                    passion = txt;
                }
                if (circulo == 6) {
                    mission = txt;
                }
                if (circulo == 7) {
                    profession = txt;
                }
                if (circulo == 8) {
                    vocation = txt;
                }

            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        class VennView extends View {
            public VennView(Context context) {
                super(context);

            }

            void update(Canvas canvas) {

                //canvas.drawPaint(paint);

                //clear previous drawings

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

                //clear previous drawings

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
                //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));

                PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;
                paint.setXfermode(new PorterDuffXfermode(mode));

                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);
                // c.drawBitmap(bitmap, 30, 30, paint);


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
                c.drawText("Whats you Love ", cx - tx + 40, cy - tx * 2 - 20, paint);
                c.drawText(" LOVE ", cx - tx + 110, cy - tx * 2 + 30, paint);


                c.drawText("what you are", cx / 14, cy + ty - 40, paint);
                c.drawText(" GOOD AT", cx / 14, cy + ty - 10, paint);

                c.drawText("what the world", cx + tx + 30, cy + ty - 30, paint);
                c.drawText(" NEED", cx + tx + 84, cy + ty, paint);

                c.drawText("Whats you can be", cx - tx + 25, cy + ty * 7, paint);
                c.drawText(" PAID FOR ", cx - tx + 75, cy + ty * 8, paint);


                paint.setTextSize(24);
                c.drawText("PASSION ", cx - tx - 30, cy - tx + 20, paint);
                c.drawText("MISSION ", cx + tx - 60, cy - tx + 20, paint);
                c.drawText("PROFESSION ", cx - tx - 70, cy + tx - 20, paint);
                c.drawText("VOCATION ", cx + tx - 60, cy + tx - 20, paint);


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

                            inpurDialog(1);
                        }

                        if (c2 == true && cum == false && c3 == false && c4 == false) {
                            circulodavez = 2;

                            inpurDialog(2);
                        }


                        if (c3 == true && c2 == false && cum == false && c4 == false) {
                            circulodavez = 3;

                            inpurDialog(3);

                        }

                        if (c4 == true && c2 == false && c3 == false && cum == false) {
                            circulodavez = 4;

                            inpurDialog(4);

                        }


                        if (cum == true && c2 == true && c3 == false && c4 == false) {
                            white("Your Passion", 5);

                        }


                        if (cum == true && c2 == false && c3 == true && c4 == false) {
                            white("Your Mission", 6);

                        }

                        if (c1 == false && c2 == true && c3 == false && c4 == true) {
                            white("Your Profession", 7);
                        }

                        if (c1 == false && c2 == false && c3 == true && c4 == true) {
                            white("Your Vocation", 8);
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
