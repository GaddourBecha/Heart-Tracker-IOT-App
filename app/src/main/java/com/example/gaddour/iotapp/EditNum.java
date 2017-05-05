package com.example.gaddour.iotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EditNum extends AppCompatActivity {

    TextView t;
    EditText s;
    Button b;
    ArrayList< AccessValeur> A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_num);


        t= (TextView) findViewById(R.id.textView13);
        s = (EditText) findViewById(R.id.editText);
        b= (Button) findViewById(R.id.button4);


        t.setX(0);
        t.setY(100);

        s.setX(300);
        s.setY(500);

        b.setX(400);
        b.setY(820);


        A= new ArrayList<>();
        DatabaseHandler db= new DatabaseHandler(this);

        A=db.getAllValeur();

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(A.size()>0) {
                    String val = A.get(A.size() - 1).getBat();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage( s.getText().toString(), null, "ALERT : heart rate is "+ val,null,null);
                    Toast.makeText(EditNum.this,"message sent", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
