package com.example.pattern;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Select extends AppCompatActivity {

    //variables
    Button b1,b2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_select);

        //hooks
        b1=findViewById(R.id.guess);
        b2=findViewById(R.id.generate);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity1();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity2();
            }
        });
    }

    public void activity1(){
        Intent i1 = new Intent(this,GuessAutomataActivity.class);
        startActivity(i1);
    }

    public void activity2(){
        Intent i2 = new Intent(this,GenerateActivity.class);
        startActivity(i2);
    }

}