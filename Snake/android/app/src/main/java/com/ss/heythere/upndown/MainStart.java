package com.ss.heythere.upndown;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainStart extends Activity {

    MediaPlayer stsong;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_start);

        stsong = MediaPlayer.create(MainStart.this,R.raw.oggy);
        stsong.start();
        Thread timer = new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    try {
                        Class selclass=Class.forName("com.ss.heythere.upndown.menu");
                        Intent callThem= new Intent(MainStart.this,selclass);
                        startActivity(callThem);


                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();}
                }}};
        timer.start();


    }
    protected void onPause(){
        super.onPause();
        stsong.release();
        finish();
    }
}
