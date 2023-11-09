package com.tsuman.rcremote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.marcoscg.ipcamview.IPCamView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RcController0 extends AppCompatActivity {

    VideoView videoView;

    SurfaceGraphics surfaceGraphics;
    BluetoothService btservice;
    BluetoothUno btUno;
    Intent intent;
    Boolean btIsBound = false;
    String imgURL = "http://192.168.1.193:8080/shot.jpg";

    private static String LOG_TAG = "com.tsuman.rcremote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent in = getIntent();
        String mac = in.getStringExtra("btMAC");
        String ipaddr = in.getStringExtra("wifiIP");

        System.out.println("The BTMAC is : "+mac+" WIFI IP is : "+ipaddr);

        imgURL = "http://"+ipaddr.trim()+"/shot.jpg";

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        this.setContentView(findViewById(R.id.activity_rc_remote));
//        this.setContentView(R.layout.activity_rc_remote);

        surfaceGraphics = new SurfaceGraphics(this);
//        surfaceGraphics = (SurfaceGraphics) findViewById(R.id.surfView);
//        surfaceGraphics.setZOrderMediaOverlay(false);

        intent = new Intent(this, BluetoothService.class);
        startService(intent);
        btservice = new BluetoothService();
        btservice.mac = mac;
        btUno = new BluetoothUno(btservice);

        // Layout, on which I'm adding Surfaceview programmatically
//        FrameLayout layout = (FrameLayout) findViewById(R.id.activity_rc_remote);
        FrameLayout layout = new FrameLayout(getApplicationContext());
        layout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        ));
        layout.addView(surfaceGraphics);

        // Layout which should be top over everything
//        videoView = (VideoView) findViewById(R.id.videoView);

//        videoView = new VideoView(getApplicationContext());
//        videoView.setMinimumHeight(320);
//        videoView.setMinimumWidth(320);
//        videoView.setVideoURI(getMedia("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
////        videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
//
//        MediaController controller = new MediaController(this);
//        controller.setMediaPlayer(videoView);
//        videoView.setMediaController(controller);
//        // Although I'm adding SurfaceView first and Relative second, no hierarchy order is visible...
//        layout.addView(videoView);

//        IPCamView camView = new IPCamView(getApplicationContext());
//        camView.setMinimumHeight(320);
//        camView.setMinimumWidth(320);
//        camView.setUrl("http://192.168.1.193:8080");
//        camView.setInterval(1000); // In milliseconds, default 1000
//        camView.start();
//        layout.addView(camView);

//        ImageView imgView = new ImageView(getApplicationContext());
//        imgView.setMinimumHeight(320);
//        imgView.setMinimumWidth(320);
////        Bitmap img = LoadImageFromWebOperations("http://192.168.1.193:8080/shot.jpg");
////        imgView.setImageBitmap(img);
//        layout.addView(imgView);
//        new GetImageFromUrl(imgView).execute("http://192.168.1.193:8080/shot.jpg");


//        WebView webView = new WebView(getApplicationContext());
//        webView.setMinimumHeight(320);
//        webView.setMinimumWidth(320);
//        webView.loadUrl("http://192.168.1.193:8080/video");
////        webView.loadUrl("http://192.168.1.193:8080/browserfs.html");
//        System.out.println("Height of View == "+webView.getHeight());
//        webView.setInitialScale(100);
//        layout.addView(webView);


        this.setContentView(layout);
        hide();
//        videoView.seekTo(1);
//        videoView.start();
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        ImageView imgView;

        public GetImageFromUrl(ImageView img) {
            this.imgView = img;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }
//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public static Bitmap LoadImageFromWebOperations(String url) {
        String name = url;
        URL url_value = null;
        try {
            url_value = new URL(name);
            Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
            return mIcon1;
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            return null;
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }


    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // you can also put a video file in raw package and get file from there as shown below
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        surfaceGraphics.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        mVisible = false;
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

    public class SurfaceGraphics extends SurfaceView implements Runnable, SurfaceHolder.Callback {

//        ImageView urlImageHolder;
//        GetImageFromUrl getImageFromUrl;

        SurfaceHolder ourHolder;
        Paint paint;
        Bitmap scaled, steer;
        Matrix transform = new Matrix();

        //        float yy = 200;
//        float xx = 200;
//        float xs, ys, xe, ye;
//
        int screenWidth = 0, screenHeight = 0;

        ///
        int handPointer = 100, accPointer = 100;
        float handx, handy, accx, accy;
        private static final int SIZE = 60;

        /// Finger pointer index --> requires to determine which finger is pressing where !!
        private SparseArray<PointF> mActivePointers;
        private Paint mPaint;
        private int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA,
                Color.BLACK, Color.CYAN, Color.GRAY, Color.RED,
                Color.DKGRAY,
                Color.LTGRAY, Color.YELLOW};
        private TextPaint textPaint;

        public SurfaceGraphics(Context context) {
            super(context);
            paint = new Paint();

            /// Finger pointer index --> requires to determine which finger is pressing where !!
            mActivePointers = new SparseArray<PointF>();

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.GREEN);
            textPaint = new TextPaint();
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setTextSize(80);
            textPaint.setShadowLayer(3, 1, 1, 0x88aaaa0a);
            textPaint.setAntiAlias(true);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            //  paint.setShadowLayer(3,1,1,0xcc0aaa0a);
            ourHolder = getHolder();
            ourHolder.addCallback(this);

        }

//        public Bitmap centerCrop(Bitmap srcBmp, ){
//            if (srcBmp.getWidth() >= srcBmp.getHeight()){
//                dstBmp = Bitmap.createBitmap(
//                        srcBmp,
//                        srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
//                        0,
//                        srcBmp.getHeight(),
//                        srcBmp.getHeight()
//                );
//
//            }else{
//                dstBmp = Bitmap.createBitmap(
//                        srcBmp,
//                        0,
//                        srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
//                        srcBmp.getWidth(),
//                        srcBmp.getWidth()
//                );
//            }
//        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // load car interior
            int newWidth, newHeight;

            Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.no_stering_wheel);
//            float scale = (float) background.getHeight() / (float) getHeight();
//            newWidth = Math.round(background.getWidth() / scale);
//            newHeight = Math.round(background.getHeight() / scale);
            scaled = ThumbnailUtils.extractThumbnail(background, getWidth(), getHeight());
//            scaled = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), true);

            // load sterring wheel
            steer = BitmapFactory.decodeResource(getResources(), R.drawable.wheelss2);
            newWidth = Math.round(steer.getWidth() / 2);
            newHeight = Math.round(steer.getHeight() / 2);
            steer = Bitmap.createScaledBitmap(steer, newWidth, newHeight, true);
            new Thread(this).start();

//            getImageFromUrl = new GetImageFromUrl(urlImageHolder);
//            getImageFromUrl.execute("http://192.168.1.193:8080/shot.jpg");
            screenWidth = getWidth();
            screenHeight = getHeight();

            fetchLatestImageURL.start();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);
            int maskedAction = event.getActionMasked();
            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    PointF f = new PointF();
                    f.x = event.getX(pointerIndex);
                    f.y = event.getY(pointerIndex);
                    startPointer(f, pointerIndex);
                    mActivePointers.put(pointerId, f);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    for (int size = event.getPointerCount(), i = 0; i <
                            size; i++) {
                        PointF point = mActivePointers.get
                                (event.getPointerId(i));
                        if (point != null) {
                            point.x = event.getX(i);
                            point.y = event.getY(i);
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL: {
                    PointF f = new PointF();
                    f.x = event.getX(pointerIndex);
                    f.y = event.getY(pointerIndex);
                    endPointer(f, pointerIndex);
                    mActivePointers.remove(pointerId);
                    break;
                }
            }
            return true;
        }


        // on touch DOWN
        private void startPointer(PointF p, int i) {
            float x = p.x, y = p.y;

            /// new handPointer in stering wheel region
            if (x < getWidth() * 0.75 && handPointer > 5) {
                handPointer = i;
                if (handPointer == accPointer) accPointer++;

                /// new poiner in the break region
            } else if (x > getWidth() - 200 && y > getHeight() - 100) {
                brake = true;

                /// new pointer in the reverse region
            } else if (x > getWidth() - 450 && x < getWidth() - 250 && y > getHeight() - 100) {
                reverse = !reverse;
                if (sp > 1) reverse = !reverse;

                /// new accPointer in the acceleration region (usually pointer index is <=5, >5 means nothing)
            } else if (x > getWidth() * 0.75 && accPointer > 5) {
                accPointer = i;
                if (handPointer == accPointer) handPointer++;
            }

        }

        /// on touch UP
        private void endPointer(PointF p, int i) {

            /// if handPointer is removed removed
            if (i == handPointer) {
                if (handPointer == 0 && accPointer < 5) {
                    accPointer = 0;
                }
                handPointer = 100;
                handx = p.x;
                handy = p.y;
                /// if accPointer is removed from screen
            } else if (i == accPointer) {
                if (accPointer == 0 && handPointer < 5) {
                    handPointer = 0;
                }
                accPointer = 100;
                accx = p.x;
                accy = p.y;
            }

        }

        /// value of acceleration and angle
        double ang = 1000000, an, speed = 1000000, sp, prev_an = 0.5, prev_sp = 0.5;
        PointF point;

        // for automatic adjustment and control
        boolean autoAccln = false, autoSteer = false, brake = false, reverse = false;
        boolean brk = false; // for printing B-Red..


        @Override
        public void run() {

            // initially if bluetooth is not started
            while (!btIsBound) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("--- Car Controller Thread Started");
            Log.v(LOG_TAG, "Car Controller Thread Started");

            /// center of wheel x and wheel y
//            double whx = getWidth() / 10 + steer.getWidth() / 2;
//            double why = getHeight() / 10 + steer.getHeight() / 2;
            double whx = steer.getWidth() * 0.5 + steer.getWidth() / 2;
            double why = getHeight() * 0.5;

            long times = System.currentTimeMillis();
            while (true) {
                if (!ourHolder.getSurface().isValid())
                    continue;

                Canvas canvas = ourHolder.lockCanvas(null);
                try {
                    canvas.drawBitmap(scaled, 0, 0, null);
                    if (imgWebcam != null) {
                        canvas.drawBitmap(imgWebcam, (int)(screenWidth/2-imgWebcam.getWidth()/2), (int)(screenHeight/2-imgWebcam.getHeight()/2), null);
                    }

                    /// show/draw the pointers
                    for (int size = mActivePointers.size(), i = 0; i < size; i++) {
                        point = mActivePointers.valueAt(i);
                        if (point != null)
                            mPaint.setColor(colors[i % 9]);
                        canvas.drawCircle(point.x, point.y, SIZE, mPaint);
                    }

                    // if break was pressed
                    if (brake) {
                        speed = 1000000;
                        sp = 0;
                        brake = false;
                    }

                    if (handPointer < 5) {
                        point = mActivePointers.valueAt(handPointer);
                        if (autoSteer) {
                            autoSteer = false;
                            double ann = Math.atan2(why - point.y, whx - point.x) * 57.2957795131;
                            ann += 90.0;
                            if (ann > 180) ann = ann - 360;
                            else if (ann < -180) ann = 360 + ann;
                            ang = ann - an;
                        }
                        an = Math.atan2(why - point.y, whx - point.x) * 57.2957795131;
                        an += 90.0;
                        if (an > 180) an = an - 360;
                        if (ang > 999999) {
                            ang = an;
                            an = 0;
                        } else {
                            an = an - ang;
                        }
                        if (an > 180) an = an - 360;
                        else if (an < -180) an = 360 + an;
                    } else {
                        if (ang < 999999) new Thread(steringThread).start();
                        ang = 1000000;
                    }
                    if (an > 120) {
                        ang = ang + (an - 120);
                        an = 120;
                    } else if (an < -120) {
                        ang = ang + (an + 120);
                        an = -120;
                    }
                    if (accPointer < 5) {
                        point = mActivePointers.valueAt(accPointer);
                        if (autoAccln) {
                            autoAccln = false;
                            speed = sp + point.y;
                        }
                        sp = point.y;
                        if (speed > 999999) {
                            speed = sp;
                            sp = 0;
                        } else {
                            sp = speed - sp;
                        }
                        if (sp > 400) sp = 400;
                        else if (sp < 0) sp = 0;
                    } else {
                        if (speed < 999999) new Thread(acclnThread).start();
                        speed = 1000000;
                    }
                    //  canvas.drawText("Ang: " + an +" " + ang +"  Spe: " + sp + " " + speed, 10, 40, textPaint);
                    if (!reverse) textPaint.setARGB(150, 50, 200, 50);
                    else textPaint.setARGB(200, 200, 50, 50);
                    canvas.drawText("R", getWidth() - 375, getHeight() - 25, textPaint);

                    if (sp != 0) textPaint.setARGB(150, 50, 200, 50);
                    else textPaint.setARGB(200, 200, 50, 50);
                    canvas.drawText("B", getWidth() - 125, getHeight() - 25, textPaint);

                    float left = getWidth()*0.8f;
                    float right = left+100;
                    float top = getHeight() - 200;
                    float bottom = top + 30;
                    for (int a = 0; a <= sp * 0.04; a++) {
                        paint.setARGB(150, 60 + a * 12, 254 - a * 12, 60);
                        canvas.drawRoundRect(left, top - a * 35, right, bottom - a * 35, 10, 10, paint);
                    }

//                    System.out.println("W,H = "+getWidth()+", "+getHeight());
                    transform.setTranslate((float) (steer.getWidth() * 0.5), (float) (getHeight() * 0.5 - steer.getHeight() * 0.5));
                    transform.preRotate((float) an, steer.getWidth() / 2, steer.getHeight() / 2);
                    canvas.drawBitmap(steer, transform, null);
                    if (System.currentTimeMillis() - times > 99) {
                        if (btservice.socketConnected && !btservice.writing) {

                            if (prev_an != an || prev_sp != sp){
                                setSpeedStering(reverse?-sp:sp, an);
                                prev_sp = sp;
                                prev_an = an;
                            }
//                        btservice.write("angle="+an+"\nspeed="+(reverse?-sp:sp));
                        }
                        times = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                } finally {
                    if (ourHolder.getSurface().isValid())
                        if (ourHolder != null) ourHolder.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public void setSpeedStering(double speed, double stering){
            /// speed should be in range -400 to 400;
            /// stering should be in range -120, 120 degrees;

            /// use the stering value to scale the speed for each wheel
            double scaler = Math.cos(Math.toRadians(stering/120.0*90.0));
            double speedL = speed/400.0*255.0;
            double speedR = speed/400.0*255.0;
            if (stering < 0){
                /// stering towards left
                speedL = speedL*scaler;
            }else{
                /// stering towards right
                speedR = speedR*scaler;
            }

            //// left motor is fast in forward... even faster in reverse.. so adusting;
            if (speedL > 0){
                speedL = speedL*0.9;
            }else {
                speedL = speedL*0.85;
            }

            System.out.println("The speed LR is:"+speedL+" "+speedR);

            btUno.rotateLeftWheel((int) speedL);
            btUno.rotateRightWheel((int) speedR);


        }

        Thread acclnThread = new Thread(new Runnable() {
            @Override
            public void run() {
                autoAccln = true;
                while (autoAccln) {
                    if (sp > 0.1)
                        sp--;
                    else {
                        sp = 0;
                        autoAccln = false;
                        break;
                    }
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread steringThread = new Thread(new Runnable() {
            @Override
            public void run() {
                autoSteer = true;
                while (autoSteer) {
                    if (Math.abs(an) > 1)
                        if (an < 0) an++;
                        else an--;
                    else {
                        an = 0;
                        autoSteer = false;
                        break;
                    }
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Bitmap imgWebcam;
        int intervalMilis = 1000 / 15;
        Thread fetchLatestImageURL = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Bitmap loadImg = LoadImageFromWebOperations(imgURL);


                    if (loadImg != null) {

                        /// resize to fit screen width, height
//                    int newWidth = getWidth();
//                    int newHeight = getHeight();

//                        System.out.println("H,W = "+screenHeight+" ,"+screenWidth);
//                        System.out.println("img H,W = "+loadImg.getHeight()+" ,"+loadImg.getWidth());

                        float factor = 0;
                        if (screenWidth/screenHeight < loadImg.getWidth()/loadImg.getHeight()){
                            factor = (float)screenWidth/(float)loadImg.getWidth();
                        }else{
                            factor = (float) screenHeight/(float) loadImg.getHeight();
                        }



//                    float factor = Math.max((float)loadImg.getWidth()/(float)newWidth, (float)loadImg.getHeight()/(float)newHeight);
                        int newWidth = (int) (loadImg.getWidth() * factor);
                        int newHeight = (int) (loadImg.getHeight() * factor);
                        loadImg = Bitmap.createScaledBitmap(loadImg, newWidth, newHeight, true);

//                        Matrix mat = new Matrix();
//                        mat.postRotate(90);
//                        loadImg = Bitmap.createBitmap(loadImg, 0, 0, loadImg.getWidth(), loadImg.getHeight(), mat, true);

                        imgWebcam = loadImg;
                    }
                    try {
                        Thread.sleep(intervalMilis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
