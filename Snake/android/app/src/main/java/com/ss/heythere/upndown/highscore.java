package com.ss.heythere.upndown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class highscore extends Activity {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.highscore);
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                      String rea = "",text = "";
        TextView textView=(TextView)findViewById(R.id.textscore);
        final File fil=new File(path,"snake");
                                      try {
                                          DataInputStream inp =new DataInputStream(new FileInputStream(fil));
                                          BufferedReader imp = new BufferedReader(new InputStreamReader(inp));
                                            int k=0;
                                          while((rea = imp.readLine())!=null){
                                              System.out.println(rea);
                                              text=text+"\t"+imp.readLine()+"\t"+imp.readLine()+"\n";

                                          }

                                          imp.close();
                                      } catch (FileNotFoundException e) {
                                          e.printStackTrace();
                                      } catch (IOException e) {
                                          e.printStackTrace();
                                      }
                                            textView.setText(rea);
                                            System.out.println(text);
                                      Button breset= (Button)findViewById(R.id.button4);
                                      Button bback= (Button)findViewById(R.id.button3);
                                      bback.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              setContentView(R.layout.menu_layout);
                                              finish();
                                              Class selclass = null;
                                              try {
                                                  selclass = Class.forName("com.ss.heythere.upndown.menu");
                                              } catch (ClassNotFoundException e) {
                                                  e.printStackTrace();
                                              }
                                              Intent callThem = new Intent(highscore.this, selclass);
                                              startActivity(callThem);
                                          }
                                      });
                                      breset.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              fil.delete();
                                              setContentView(R.layout.menu_layout);
                                              finish();
                                              Class selclass = null;
                                              try {
                                                  selclass = Class.forName("com.ss.heythere.upndown.menu");
                                              } catch (ClassNotFoundException e) {
                                                  e.printStackTrace();
                                              }
                                              Intent callThem = new Intent(highscore.this, selclass);
                                              startActivity(callThem);
                                          }
                                      });}
    protected void onPause(){
        super.onPause();
        finish();

    }
        }