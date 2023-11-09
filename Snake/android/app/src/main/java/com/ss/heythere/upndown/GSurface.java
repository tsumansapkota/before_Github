package com.ss.heythere.upndown;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GSurface extends Activity implements View.OnTouchListener{
    MyBringBackSurface ourSurfaceView;
    float x,y,sx,sy,fx,fy,dx,dy,anix,aniy,scaledx,scaledy;
    //Bitmap test, plus;
    Typeface font;
    int kas;
    int snakelen=5,score=0;
    boolean mrec=true;
    float twtx=200,hardness=10,ud=0,lr=1,scx,scy;
    float[] snakx=new float[100];
    float[] snaky=new float[100];
    boolean gamover=false;
    long tscr=0;
    long highscore;
    Random random =new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        snakx[0]=220;
        Intent callThem= getIntent();
        int scrollvalue = callThem.getIntExtra("scrollvalue",10);
        hardness=20-scrollvalue;
        snaky[0]=60;
        for(int a=0; a==0;){
            scx = random.nextInt(22)*20+20;
            scy = random.nextInt(37)*20+20;
            for(int ab=0; ab<snakelen; ab++){
                if(scx==snakx[ab] && scx==snaky[ab]){a=0;break;}
                a=1;
            }

        }
        for(int a=1; a<snakelen; a++){
            snakx[a]=snakx[0]-20*(a);
            snaky[a]=60;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ourSurfaceView=new MyBringBackSurface(this);
        ourSurfaceView.setOnTouchListener(this);
        x=0;y=0;sx=0;sy=0;fx=0;fy=0;
        dx=dy=anix=aniy=scaledx=scaledy=0;
        setContentView(ourSurfaceView);
      //  test= BitmapFactory.decodeResource(getResources(),R.drawable.s2);
       // plus= BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        SeekBar sb =(SeekBar)findViewById(R.id.seekBar);
        kas=R.string.scroll;

       /*
*/


    }
//process handle
    @Override
    protected void onResume() {
        super.onResume();
        ourSurfaceView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            ourSurfaceView.pause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


   // main events here
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //thread here

        try {
            Thread.sleep(30+ ((int) hardness)*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x=event.getX();
        y=event.getY();
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                sx=event.getX();
                sy=event.getY();
                dx=dy=anix=aniy=scaledx=scaledy=fx=fy=0;
                break;
            case MotionEvent.ACTION_UP:
                fx=event.getX();
                fy=event.getY();
                dx=fx-sx;
                dy=fy-sy;
                scaledx=dx/30;
                scaledy=dy/30;
                x=y=0;
                break;
        }
        return true;
    }
    public class MyBringBackSurface extends SurfaceView implements Runnable{
        Paint textPaint;
        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean isRunning=false;
        float xx=1,yy=0;
        int exx=-50,exy=-50;
        float exscore=0;
        int moves=0;
        boolean gamfirsttime=false;
        menu men=new menu();

        public MyBringBackSurface(Context context) {
            super(context);
            font = Typeface.createFromAsset(context.getAssets(), "CENTURY.TTF");
            textPaint = new Paint();
            textPaint.setARGB(30,255,10,50);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(50);
            textPaint.setTypeface(font);
            ourHolder=getHolder();

               }
        public void pause() throws InterruptedException {
            isRunning=false;
            while(true){
                ourThread.join();
                break;
            }
            ourThread=null;

        }

        public void resume(){
            isRunning=true;
            ourThread=new Thread(this);
            ourThread.start();


        }

        public void run(){
            while(isRunning){
                if(!ourHolder.getSurface().isValid())
                     continue;


                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawRGB(255, 255, 200);
                if(!gamover) {
                /*    if (x != 0 && y != 0) {
                        canvas.drawBitmap(test, x - (test.getWidth() / 2), y - (test.getHeight() / 2), null);
                    }
                    if (sx != 0 && sy != 0) {
                        canvas.drawBitmap(plus, sx - (plus.getWidth() / 2), sy - (plus.getHeight() / 2), null);
                    }
                    if (fx != 0 && fy != 0) {
                        canvas.drawBitmap(plus, fx - (plus.getWidth() / 2), fy - (plus.getHeight() / 2), null);

                    }*/
                    if (dx > 0 && (dx * dx) > (dy * dy) && lr != -1&&mrec) {
                        xx = 1;
                        yy = 0;
                        lr = 1;
                        ud = 0;mrec=false;
                    }
                    else if (dx < 0 && (dx * dx) > (dy * dy) && lr != 1&&mrec) {
                        xx = -1;
                        yy = 0;
                        lr = -1;
                        ud = 0;mrec=false;
                    }
                   else if (dy > 0 && (dx * dx) < (dy * dy) && ud != -1&&mrec) {
                        xx = 0;
                        yy = 1;
                        lr = 0;
                        ud = 1;mrec=false;
                    }
                   else if (dy < 0 && (dx * dx) < (dy * dy) && ud != 1&&mrec) {
                        xx = 0;
                        yy = -1;
                        lr = 0;
                        ud = -1;mrec=false;
                    }

                    if(twtx>hardness){
                        moves--;
                    twtx = 0;
                    //eating
                    if (snakx[0] == scx && snaky[0] == scy) {
                        for (int a = 0; a == 0; ) {
                            scx = random.nextInt(23) * 20+20;
                            scy = random.nextInt(39) * 20+20;
                            for (int ab = 0; ab < snakelen; ab++) {
                                if (scx == snakx[ab] && scx == snaky[ab]) {
                                    a = 0;
                                    break;
                                }
                                a = 1;
                            }
                        }
                        snakelen++;
                        score++;
                        if((score+1)%5==0){
                            for (int a = 0; a == 0; ) {
                                exx = random.nextInt(23) * 20+20;
                                exy = random.nextInt(39) * 20+20;
                                for (int ab = 0; ab < snakelen; ab++) {
                                    if ((exx == snakx[ab] && exy == snaky[ab])||(exx==scx&&exy==scy)) {
                                        a = 0;
                                        break;
                                    }
                                    a = 1;
                                    moves=50;
                                }

                            }
                    }
                    }//eatinguptohere
                        if(moves<=0){exx=-50;exy=-50;}
                        if (snakx[0] == exx && snaky[0] == exy) {
                                exscore=exscore+moves*(20-hardness);
                                exx=-50;exy=-50;
                        }

                        for (int a = snakelen - 1; a > 0; a--) {
                        snakx[a] = snakx[a - 1];
                        snaky[a] = snaky[a - 1];
                    }
                    snakx[0] = snakx[0] + (xx * 20);
                    snaky[0] = snaky[0] + (yy * 20);
                        mrec=true;
                    if (snakx[0] < 10) snakx[0] = 480;
                    if (snaky[0] < 10) snaky[0] = 800;
                    if (snakx[0] > 480) snakx[0] = 20;
                    if (snaky[0] > 800) snaky[0] = 20;
                    for (int ab = 1; ab < snakelen; ab++) {
                        if (snakx[0] == snakx[ab] && snaky[0] == snaky[ab]) {
                            gamover = true;
                            //	 dispose();
                        }
                    }
                }
                    twtx++;

                     tscr= (int) (score*(20-hardness)+exscore);
                  String textdisplay = String.format("%d",tscr);
                    canvas.drawText(textdisplay, 50, 50, textPaint);
                    Paint circl = new Paint();
                    circl.setARGB(180, 19, 200, 30);
                    for (int ab = 0; ab < snakelen; ab++) {
                        canvas.drawCircle(snakx[ab] - 10, snaky[ab] -10, 10, circl);
                    }
                    circl.setARGB(200, 190, 200, 20);
                    canvas.drawCircle(scx - 10, scy - 10, 10, circl);
                    circl.setARGB(225,225 , 225, 10);
                    canvas.drawCircle(exx - 10, exy - 10, 15-((int)(twtx%3)+(int)(moves%3)), circl);
                }//not gameover
                else {
                    String textdisplay = String.format("Your Score: %d",tscr);
                    textPaint.setARGB(200, 200, 70, 10);

                            canvas.drawText("GAMEOVER!!", 200, 300, textPaint);
                            canvas.drawText(textdisplay, 200, 400, textPaint);
                   if(gamfirsttime){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }}
                            File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            File fil=new File(path,"snake");
                            try {
                                FileWriter filw = new FileWriter(fil);
                                String kkk=String.format("%d",tscr);
                                filw.write(kkk);
                                filw.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Class selclass=Class.forName("com.ss.heythere.upndown.menu");
                                Intent callThem= new Intent(GSurface.this,selclass);
                                startActivity(callThem);


                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();}
                    gamfirsttime=true;
                }

                ourHolder.unlockCanvasAndPost(canvas);

            }
        }
    }


}
