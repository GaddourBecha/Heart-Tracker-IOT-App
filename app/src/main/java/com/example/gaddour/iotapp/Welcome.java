package com.example.gaddour.iotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity {

    Button b1,b2;
    TextView last,lastVal,min,minVal,ave,aveVal,max,maxVal;
    ArrayList< AccessValeur> A= new ArrayList<>();
    DatabaseHandler db= new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        b1 =  (Button) findViewById(R.id.button);
        b2 =  (Button) findViewById(R.id.button2);
        b1.setX(380);
        b1.setY(520);
        b2.setX(380);
        b2.setY(820);


        last  = (TextView) findViewById(R.id.textView3);
        lastVal = (TextView) findViewById(R.id.textView4);
        min  = (TextView) findViewById(R.id.textView5);
        minVal  = (TextView) findViewById(R.id.textView6);
        ave  = (TextView) findViewById(R.id.textView7);
        aveVal  = (TextView) findViewById(R.id.textView8);
        max  = (TextView) findViewById(R.id.textView9);
        maxVal = (TextView) findViewById(R.id.textView10);


        last.setX(0);
        last.setY(1000);

        lastVal.setX(0);
        lastVal.setY(1200);

        min.setX(0xfffffe0c);
        min.setY(1450);

        minVal.setX(0xfffffe0c);
        minVal.setY(1650);

        ave.setX(0);
        ave.setY(1450);

        aveVal.setX(0);
        aveVal.setY(1650);


        max.setX(500);
        max.setY(1450);

        maxVal.setX(500);
        maxVal.setY(1650);




        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Measure.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, EditNum.class);
                startActivity(intent);
            }
        });





        A=db.getAllValeur();
        int Moy=0;
        int Max=0;
        int Min=150;

        for (int i=0; i<A.size();i++){
            Log.e("bat",A.get(i).getBat());

            Log.e("Date",A.get(i).getDate());
            Moy+= Integer.valueOf(A.get(i).getBat()) ;
            if(Integer.valueOf(A.get(i).getBat())>Max)
                Max=Integer.valueOf(A.get(i).getBat()) ;
            if(Integer.valueOf(A.get(i).getBat())<Min)
                Min=Integer.valueOf(A.get(i).getBat()) ;

        }
if(A.size()>0) {

    lastVal.setText(A.get(A.size() - 1).getBat());
    minVal.setText(String.valueOf(Min));
    aveVal.setText(String.valueOf(Moy/A.size()));
    maxVal.setText(String.valueOf(Max));

}else {
    lastVal.setText("");
    minVal.setText("");
    aveVal.setText("");
    maxVal.setText("");
}







    }
}
