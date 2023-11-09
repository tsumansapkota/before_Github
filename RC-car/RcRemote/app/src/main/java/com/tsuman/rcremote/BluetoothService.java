package com.tsuman.rcremote;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BluetoothService extends Service {

    public BluetoothService() {
    }

    private final IBinder binder = new MyLocalBinder();

    public class MyLocalBinder extends Binder {
        BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    OutputStream output;
    InputStream input;
    BluetoothSocket socket;
    String mac = "98:D3:34:90:8C:EA";
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    boolean socketConnected = false, writing = false;

    boolean tryConnection = false;
    Thread connectThread;

    private static String LOG_TAG = "com.tsuman.rcremote";
    StringBuilder log;

    @Override
    public void onCreate() {
        super.onCreate();
        log = new StringBuilder();
        Log.v(LOG_TAG, "-- onCreate");
        log.append("Service Created\n");
    }

    public void addLog(String string) {
        log.append(string);
        return;
    }

    public void write(byte[] data) {
        if (!socketConnected) return;
        writing = true;
        try {
            if (data.length > 0)
                output.write(data);
        } catch (IOException e) {
            socket = null;
            socketConnected = false;
            e.printStackTrace();
        }
        writing = false;
    }

    public byte[] read(byte[] inp, int length) {
        if (!socketConnected) return null;

        if (inp != null) {
            write(inp);
        }

        writing = true;
        byte[] bts = new byte[256];
        try {
            int inputLen = input.read(bts);
            writing = false;
            return Arrays.copyOfRange(bts, 0, inputLen);
        } catch (IOException e) {
            e.printStackTrace();
            log.append("BT Data read Exception\n");
            Log.v(LOG_TAG, "--BT Data read Exception");
            writing = false;
            return null;
        }
    }

    public void startConnection() {
        log.append("Start BT Connection ...\n");
        Log.v(LOG_TAG, "--Start BT Connection ...");


        if (connectThread != null) {
            stopConnection();
        }
        tryConnection = true;
        connectThread = new Thread(bluetoothConnectionRunnable);
        connectThread.start();
    }

    public void stopConnection() {
        if (connectThread.isAlive() || connectThread !=null ) {
            tryConnection = false;
            try {
                connectThread.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.append("Unable to close Bluetooth connection thread, interrupting !! \n");
                Log.v(LOG_TAG, "--Unable to close Bluetooth connection thread, interrupting !! ");
                connectThread.interrupt();
                connectThread = null;
            }

        }
    }

    Runnable bluetoothConnectionRunnable = new Runnable() {
        @Override
        public void run() {
            Log.v(LOG_TAG, "-- Thread running");
            while (tryConnection) {
                if (socket == null) {
                    // Log.v(LOG_TAG,"-- NULL socket");
                    socketConnected = false;
                    try {
                        //https://www.geeksforgeeks.org/all-about-hc-05-bluetooth-module-connection-with-android/
                        //https://github.com/infoaryan/HC-05-Android-Interfacing/blob/master/app/src/main/java/infoaryan/in/hc05_bluetooth/MainActivity.java
                        BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice hc = myBluetooth.getRemoteDevice(mac);
                        socket = hc.createRfcommSocketToServiceRecord(myUUID);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        socket.connect();

                        output = socket.getOutputStream();
                        input = socket.getInputStream();
                        if (socket.isConnected()) {
                            log.append("Connected to SERVER\n");
                            Log.v(LOG_TAG, "--Connected to BT-Server");
                            socketConnected = true;
                            output.write(new byte[]{0, 0});
                        }
                    } catch (IOException e) {
                    }
                } else {
                    if (!socket.isConnected() || output == null) {
                        log.append("Socket Open No Connection\n");
                        Log.v(LOG_TAG, "--BT-Socket Open No Connection");
                        socketConnected = false;
                        socket = null;
                    } else {
                        Log.v(LOG_TAG, "--BT-Socket Open With Connection");
                        try {
                            if (!writing) {
                                writing = true;
                                output.write(new byte[]{0, 0});
                                Log.v(LOG_TAG, "--TestDataWritten");
                                writing = false;
                            }
                        } catch (IOException e) {
                            writing = false;
                            socket = null;
                            socketConnected = false;
                            e.printStackTrace();
                        }
                    }

                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


//    public class BluetoothUno {
//        public static final int HIGH = 1;
//        public static final int LOW = 0;
//        public static final int A0 = 14;
//        public static final int A1 = 15;
//        public static final int A2 = 16;
//        public static final int A3 = 17;
//        public static final int A4 = 18;
//        public static final int A5 = 19;
//
//        int[] pinModes = new int[20];
//        int[] pinData = new int[20];
//
//        public BluetoothUno() {
//            for (int i = 0; i < 20; i++) {
//                pinModes[i] = 0;
//                pinData[i] = 0;
//            }
//        }
//
//        public void digitalWrite(int pin, int data) {
//            System.out.println("Writing digital: pin="+pin+" data="+data);
//            int b = pin << 3;
//            if (data == 0) {
//                b = b | 6;
//            } else {
//                b = b | 7;
//            }
//            write(new byte[]{(byte) b});
//            pinData[pin] = data;
//        }
//
//        public void pinMode(int pin, int mode) {
//            int b = pin << 3;
//            if (mode == 0) {
//                b = b | 5;
//            } else {
//                b = b | 4;
//            }
//            write(new byte[]{(byte) b});
//            pinData[pin] = mode;
//        }
//
//        public void analogWrite(int pin, int data) {
//            int b = pin << 3;
//            b = b | 3;
//            write(new byte[]{(byte) b, (byte) data});
//            pinData[pin] = data;
//        }
//
//        public int analogRead(int pin){
//            int b = pin << 3;
//            b = b | 2;
//            byte[] data = read(new byte[]{(byte) b}, 2);
//            pinData[pin] = (int)data[1]*128 + (int)data[0];
//            return pinData[pin];
//        }
//
//        public int digitalRead(int pin){
//            int b = pin << 3;
//            b = b | 1;
//            byte[] data = read(new byte[]{(byte) b}, 1);
//            pinData[pin] = (int)data[0];
//            return pinData[pin];
//        }
//
//    }

}
