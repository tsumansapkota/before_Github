package com.tsuman.rcremote;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tsuman.rcremote.databinding.ActivityFullscreenBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class FullscreenActivity extends AppCompatActivity {

    private final Handler mHideHandler = new Handler();
    private boolean mVisible;

    BluetoothService btservice;
    BluetoothUno btUno;
    Intent intent;
    boolean btIsBound = false;

    private ActivityFullscreenBinding binding; // = ActivityFullscreenBinding.inflate(getLayoutInflater());
    private View mContentView;
    ImageView iv;
    Button start, bluetooth, wifi, logs, settings;
    EditText wifi_ip;
    Spinner bt_mac;

    public void addItemsOnSpinner() {
        BluetoothAdapter btAdapt = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = btAdapt.getBondedDevices();
        ArrayList list = new ArrayList();

        if (devices.size() > 0) {
            for (BluetoothDevice bt : devices) {
                list.add(bt.getName() + " -> " + bt.getAddress());
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        // Adding the devices to the list with ArrayAdapter class
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        bt_mac.setAdapter(adapter);
        // Method called when the device from the list is clicked
//        devicelist.setOnItemClickListener(myListListener);

//        bt_mac.setPrompt("Select Bluetooth Device");
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        bt_mac.setSelection(dataAdapter.getCount());
//        bt_mac.setAdapter(dataAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        start = binding.start;
        bluetooth = binding.bluetoothButton;
        wifi = binding.wifiButton;
        logs = binding.logs;
        settings = binding.settings;
        bt_mac = binding.bluetoothDevice;
        wifi_ip = binding.wifiDevice;

        // setup home screen for walpapper

        iv = binding.imageView2;
        Bitmap wp = BitmapFactory.decodeResource(getResources(), R.drawable.wall2);
        Matrix mat = new Matrix();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mat.postRotate(180);
        } else {
            mat.postRotate(-90);
        }
        wp = Bitmap.createBitmap(wp, 0, 0, wp.getWidth(), wp.getHeight(), mat, true);
        iv.setImageBitmap(wp);
        setContentView(binding.getRoot());

        mVisible = true;
        mContentView = binding.imageView2;
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        hide();


        //// start bluetooth service with selected device
        addItemsOnSpinner();
        intent = new Intent(this, BluetoothService.class);
        startService(intent);

        btUno = new BluetoothUno(btservice);

        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Selected device is : ");
                String bt_select = (String) bt_mac.getSelectedItem();
                String address = bt_select.substring(bt_select.length() - 17);
                btservice.mac = address;
                System.out.println(address);

                if (btIsBound) {
                    btservice.startConnection();
                }
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
                Intent videoView = new Intent(FullscreenActivity.this, VideoViewer.class);
                videoView.putExtra("wifiIP", wifi_ip.getText());
                startActivity(videoView);
            }
        });

        /// turn led on off
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btUno.digitalWrite(13, 1);
            }
        });

        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btUno.digitalWrite(13, 0);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btservice == null || btservice.mac == null){
                    Toast.makeText(getApplicationContext(), "First Select Bluetooth Device", Toast.LENGTH_LONG);
                }else{
                    unbindService(serviceConnection);
                    Intent rcController0 = new Intent(FullscreenActivity.this, RcController0.class);
                    System.out.println("----BTMAC: "+ btservice.mac);
                    System.out.println("----WIFI IP: "+ wifi_ip.getText());

                    rcController0.putExtra("btMAC", btservice.mac);
                    rcController0.putExtra("wifiIP", wifi_ip.getText().toString());
                    startActivity(rcController0);
                }
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mVisible = false;
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mVisible = true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Bitmap wp = BitmapFactory.decodeResource(getResources(), R.drawable.wall2);
        Matrix mat = new Matrix();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mat.postRotate(180);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mat.postRotate(-90);
        }
        wp = Bitmap.createBitmap(wp, 0, 0, wp.getWidth(), wp.getHeight(), mat, true);
        iv.setImageBitmap(wp);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothService.MyLocalBinder binder = (BluetoothService.MyLocalBinder) service;
            btservice = binder.getService();
            btUno.bts = btservice;
            btIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            btIsBound = false;
        }
    };
}