package com.ss.heythere.upndown;

    import android.app.Activity;
    import android.content.Intent;
    import android.media.MediaPlayer;
    import android.os.Bundle;
    import android.os.Environment;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.SeekBar;
    import android.widget.TextView;

    import java.io.BufferedReader;
    import java.io.DataInputStream;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStreamReader;

public class menu extends Activity {
    public int scrollvalue=10;
    SeekBar sb;
    TextView tv;
        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.menu_layout);
            sb = (SeekBar) findViewById(R.id.seekBar);
            sb.setMax(19);
            sb.setProgress(10);
            tv = (TextView) findViewById(R.id.textView2);

            Button b1 = (Button) findViewById(R.id.button);
            Button b2 = (Button) findViewById(R.id.button2);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    GSurface gs = new GSurface();
                    scrollvalue = 20 - progress;
                    String sss = String.format("%d", scrollvalue);
                    tv.setText(sss);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            b1.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          try {
                                              scrollvalue = sb.getProgress();
                                              Class selclass = Class.forName("com.ss.heythere.upndown.GSurface");
                                              Intent callThem = new Intent(menu.this, selclass);
                                              callThem.putExtra("scrollvalue", scrollvalue);
                                              finish();
                                              startActivity(callThem);


                                          } catch (ClassNotFoundException e) {
                                              e.printStackTrace();
                                          }

                                      }
                                  }
            );

            b2.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          Class selclass = null;
                                          try {
                                              selclass = Class.forName("com.ss.heythere.upndown.highscore");
                                          } catch (ClassNotFoundException e) {
                                              e.printStackTrace();
                                          }
                                          Intent callThem = new Intent(menu.this, selclass);
                                          finish();
                                          startActivity(callThem);
                                      }


                                  }
            );
        }
    protected void onPause(){
            super.onPause();

        }
    }

