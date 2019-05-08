package com.galeno.drawyourikigai;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoodleView extends View {
    //limiar para decidir se o usuário moveu o dedo o suficiente
    private static final float TOUCH_TOLERANCE = 10;
    private Bitmap bitmap; //Tela inicial
    private Canvas bitmapCanvas; //Para desenhar no bitmap
    private final Paint paintScreen; //Para desenhar na tela
    private final Paint paintLine; //Para fazer as linhas
    private final Map<Integer, Path> pathMap = new HashMap<>(); //mapa dos paths atualmente sendo desenhados
    private final  Map <Integer, Point> previousPointMap = new HashMap<>(); //mapa que armazena o último ponto em cada path

    ArrayList<Path> paths = new ArrayList<Path>();

    public DoodleView (Context context, AttributeSet attributes){
        super (context, attributes);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);  //suavizar os limites das linhas
        paintLine.setColor(Color.BLACK); //cor
        paintLine.setStyle(Paint.Style.STROKE); //linha sólida
        paintLine.setStrokeWidth(5); //espessura
        paintLine.setStrokeCap(Paint.Cap.ROUND); //terminais das linhas arredondados

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //o canvas desenha no bitmap
        bitmapCanvas = new Canvas (bitmap);
        bitmap.eraseColor(Color.WHITE);//apaga usando branco
    }

    public void setDrawingColor (int color){
        //paintLine.setColor(Color.WHITE);
        paintScreen.setColor(color);

    }

    public int getDrawingColor(){
        return paintLine.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
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

        
        
        compositeCanvas.drawCircle(x / 2 , y / 2 - 150, 200, setColorPaint(paint));

        paint.setXfermode(new PorterDuffXfermode(mode));
        
        compositeCanvas.drawCircle(x / 2 , y / 2 + 150, 200, setColorPaint(paint));

        //paint.setColor(Color.argb(255,0,255,0));
        compositeCanvas.drawCircle(x / 2 - 150, y / 2, 200, setColorPaint(paint));

        //paint.setColor(Color.argb(255,255,0,0));
        compositeCanvas.drawCircle(x / 2 + 150, y / 2, 200, setColorPaint(paint));

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


}
