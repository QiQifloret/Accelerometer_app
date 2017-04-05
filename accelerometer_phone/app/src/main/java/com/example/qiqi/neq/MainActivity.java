package com.example.qiqi.neq;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor nSensor;
    private TextView textviewX;
    private TextView textviewY;
    private TextView textviewZ;
    private TextView textviewA;
    private TextView textviewB;
    private TextView textviewC;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;

    private static final int NOTIFICATION_FLAG = 1;

    private final float[] angle = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textviewX = (TextView) findViewById(R.id.x_value);
        textviewY = (TextView) findViewById(R.id.y_value);
        textviewZ = (TextView) findViewById(R.id.z_value);
        textviewA = (TextView) findViewById(R.id.alpha_value);
        textviewB = (TextView) findViewById(R.id.beta_value);
        textviewC = (TextView) findViewById(R.id.gamma_value);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_ACCELEROMETER
        nSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//TYPE_GYROSCOPE
        if (null == mSensorManager) {
            Log.d(TAG, "deveice not support SensorManager");}

             // 参数三，检测的精准度
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME
        mSensorManager.registerListener(this, nSensor, SensorManager.SENSOR_DELAY_NORMAL);//

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x= (int) event.values[0];
            int y= (int) event.values[1];
            int z= (int) event.values[2];

            textviewX.setText(String.valueOf(x));
            textviewY.setText(String.valueOf(y));
            textviewZ.setText(String.valueOf(z));

            Log.d(TAG, "X:" + x + "  Y:" + y + "  Z:" + z );

        }
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {

            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp)* NS2S ;
                angle[0] += event.values[0] * dT * 100;
                angle[1] += event.values[1] * dT * 100;
                angle[2] += event.values[2] * dT * 100;

            }
            timestamp = event.timestamp;

            int a= (int) event.values[0];
            int b= (int)event.values[1];
            int c= (int) event.values[2];
            textviewA.setText(String.valueOf(a));
            textviewB.setText(String.valueOf(b));
            textviewC.setText(String.valueOf(c));

            Log.d(TAG, "qX:" + a + "  qY:" + b + "  qZ:" + c + "    timestamp:"
                    + timestamp + "    eventtimestamp:"+ event.timestamp+ "     angle[0]"+angle[0]+"     angle[1]"+angle[1]);

        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
