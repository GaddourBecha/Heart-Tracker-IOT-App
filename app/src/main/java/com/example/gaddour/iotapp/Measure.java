package com.example.gaddour.iotapp;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Measure extends AppCompatActivity {
    TextView t,t1;
    MqttAndroidClient client;
    ArrayList< AccessValeur> A= new ArrayList<>();
    DatabaseHandler db= new DatabaseHandler(this);
    boolean aBoolean=true;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);



        t1=(TextView)findViewById(R.id.textView11);
        t=(TextView)findViewById(R.id.textView12);


        t.setX(0);
        t.setY(550);

        t1.setX(0);
        t1.setY(200);



        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.43.98:1883",
                clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("connect", "onSuccess");
                    setSubscription();

                    SensorManager mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
                    Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                    Sensor mStepCountSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                    Sensor mStepDetectSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
                    SensorEventListener _SensorEventListener=   new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE && aBoolean) {


                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                x=(int) event.values[0];
                                afficher();
                                if(x>0){
                                    t1.setText("You can remove your finger");
                                    t.setText(String.valueOf(x));
                                    db.addValues(x,currentDateTimeString);
                                }}
                            if(x>75)
                                pub(x);


                            new CountDownTimer(3000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    aBoolean=false;

                                }

                                public void onFinish() {
                                    aBoolean=true;
                                }
                            }.start();



                        }



                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                    };
                    mSensorManager.registerListener(_SensorEventListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    mSensorManager.registerListener(_SensorEventListener, mStepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    mSensorManager.registerListener(_SensorEventListener, mStepDetectSensor, SensorManager.SENSOR_DELAY_NORMAL);






                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.e("connect", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                t.setText(new String(message.getPayload()));


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void pub(int x ) {
        String topic = "test/topic";
        String message = String.valueOf(x);

        try {
            client.publish(topic, message.getBytes(),0,false);
            Log.e("clicked","ok");
        } catch ( MqttException e) {
            e.printStackTrace();

        }

    }

    public void setSubscription(){
        String topic = "test/topic";
        try{
            client.subscribe(topic,0);

        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void afficher(){

        Log.d("valeur", "onSensorChanged: "+x);

    }


}
