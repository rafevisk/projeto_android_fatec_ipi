package com.galeno.drawyourikigai.AutoDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoodleView extends View {

    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private final Paint paintScreen;
    private final Paint paintLine;
    private final int radius = 220;
    private ArrayList<Point> posicoes = new ArrayList<>();

    public DoodleView (Context context, AttributeSet attributes){
        super (context, attributes);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas (bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    public void setDrawingColor (int color){
        paintScreen.setColor(color);
    }

    public int getDrawingColor(){
        return paintLine.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        posicoes = gerarPosicoes();

        Paint paint          = new Paint();
        Paint paintClear     = new Paint();
        int x = getWidth();
        int y = getHeight();

        PorterDuff.Mode mode = PorterDuff.Mode.OVERLAY;

        paintClear.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.FILL);

        // ** clear canvas background to white**
        paintClear.setColor(Color.WHITE);
        canvas.drawPaint(paintClear);

        Bitmap compositeBitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas compositeCanvas = new Canvas(compositeBitmap);
        paintClear.setColor(Color.TRANSPARENT);


        //Circle Top
        compositeCanvas.drawCircle(posicoes.get(0).x, posicoes.get(0).y, radius, setColorPaint(paint));

        paint.setXfermode(new PorterDuffXfermode(mode));

        //Circle Botton
        compositeCanvas.drawCircle(posicoes.get(2).x, posicoes.get(2).y, radius, setColorPaint(paint));

        //Circle Left
        compositeCanvas.drawCircle(posicoes.get(1).x, posicoes.get(1).y, radius, setColorPaint(paint));

        //Circle Right
        compositeCanvas.drawCircle(posicoes.get(3).x, posicoes.get(3).y, radius, setColorPaint(paint));

        canvas.drawBitmap(compositeBitmap, 0, 0, null);

    }

    public Paint setColorPaint(Paint paint){
        Random random = new Random ();
        int r = random.nextInt(155)+100;
        int g = random.nextInt(155)+100;
        int b = random.nextInt(155)+100;
        paint.setColor(Color.argb(255, r, g, b));
        return paint;
    }
    public ArrayList<Point> gerarPosicoes(){
        ArrayList<Point> Ponteiros = new ArrayList<>();
        int x = getWidth();
        int y = getWidth();
        Point posição1 = new Point(x / 2 , y / 2 - 140);
        Point posição2 = new Point(x / 2 , y / 2 + 140);
        Point posição3 = new Point(x / 2 - 140, y / 2);
        Point posição4 = new Point(x / 2 + 140, y / 2);
        Ponteiros.add(posição1);
        Ponteiros.add(posição2);
        Ponteiros.add(posição3);
        Ponteiros.add(posição4);

        return Ponteiros;
    }







}
