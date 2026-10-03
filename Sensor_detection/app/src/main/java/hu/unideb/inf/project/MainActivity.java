package hu.unideb.inf.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor mLight; private Sensor mMagnet;
    private SensorEventListener lightsel = new LightSensorEventListener();
    private SensorEventListener magneticsel = new MagneticSensorEventListener();
    ConstraintLayout panel;
    TextView direction;
    float x0; float y0; float z0;

    FrameLayout[][] m;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panel = findViewById(R.id.panel);
        direction = findViewById(R.id.directionText);
        x0 = 0.0f; y0 = 0.0f; z0 = 0.0f;
        m = new FrameLayout[5][5];

        for(int i =0; i<5; i++) {
            for(int j =0; j<5; j++) {
                String M = "m"+i+"_"+j;
                m[i][j] = findViewById(getResources()
                        .getIdentifier(M, "id", getPackageName()));
            }
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Feliratkozunk a sensorManager figyelőjére, amihez példányt adunk meg:
        sensorManager.registerListener(lightsel, mLight, SensorManager.SENSOR_DELAY_NORMAL);

        if (mMagnet !=null) {
            sensorManager.registerListener(magneticsel, mMagnet, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightsel, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(magneticsel, mMagnet, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightsel);
        sensorManager.unregisterListener(magneticsel);
    }

    private class LightSensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float lux = sensorEvent.values[0];
            if (lux<=85.0f){
                int r = 3 * (int) lux; int g = 3 * (int) lux; int b = 3 * (int) lux;
                panel.setBackgroundColor(Color.rgb(r, g, b));

            } else panel.setBackgroundColor(Color.WHITE);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }

    private class MagneticSensorEventListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent2) {
           float x = sensorEvent2.values[0];
           float y = sensorEvent2.values[1];
           float z = sensorEvent2.values[2];

           if(Math.pow(x,2)>Math.pow(y,2)){
               if((x)>0){
                   direction.setText("Left");
                   setLeftPixels();
               } else {direction.setText("Right");
               setRightPixels();
                   }
           } else{
              if((y)>0){
                  direction.setText("Down");
                  setDownPixels();
              } else {direction.setText("Up");
              setUpPixels();
              }
           }

            x0=x; y0=y;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor2, int i) {

        }
    }

    public void setLeftPixels() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2) m[i][j].setBackgroundColor(Color.RED);
                else m[i][j].setBackgroundColor(Color.TRANSPARENT);

            }


        }
        m[0][2].setBackgroundColor(Color.RED);
        m[1][1].setBackgroundColor(Color.RED);
        m[3][1].setBackgroundColor(Color.RED);
        m[4][2].setBackgroundColor(Color.RED);
    }

    public void setRightPixels(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2) m[i][j].setBackgroundColor(Color.RED);
                else m[i][j].setBackgroundColor(Color.TRANSPARENT);

            }
        }
        m[0][2].setBackgroundColor(Color.RED);
        m[1][3].setBackgroundColor(Color.RED);
        m[3][3].setBackgroundColor(Color.RED);
        m[4][2].setBackgroundColor(Color.RED);


    }

    public void setDownPixels(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 2) m[i][j].setBackgroundColor(Color.RED);
                else m[i][j].setBackgroundColor(Color.TRANSPARENT);

            }
        }
        m[2][0].setBackgroundColor(Color.RED);
        m[3][1].setBackgroundColor(Color.RED);
        m[3][3].setBackgroundColor(Color.RED);
        m[2][4].setBackgroundColor(Color.RED);



    }

    public void setUpPixels(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 2) m[i][j].setBackgroundColor(Color.RED);
                else m[i][j].setBackgroundColor(Color.TRANSPARENT);

            }
        }
        m[2][0].setBackgroundColor(Color.RED);
        m[1][1].setBackgroundColor(Color.RED);
        m[1][3].setBackgroundColor(Color.RED);
        m[2][4].setBackgroundColor(Color.RED);

    }

    }


