package com.example.pattern;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GenerateActivity extends AppCompatActivity implements PatternLockViewListener {

    String patt;
    PatternLockView p;
    Button bgen;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_generate);
        patt="";


        //Database Reference


        //Hooks
        p=findViewById(R.id.pattern_lock_gen);
        bgen=findViewById(R.id.buttonGen);
        p.addPatternLockListener(this);
        bgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GenerateActivity.this,patt,Toast.LENGTH_SHORT).show();
                if(patt.length()!=0) {
                    myAutomata gen = new myAutomata(patt);
                    gen.automata();
                    Intent i = new Intent(GenerateActivity.this, Automata.class);
                    i.putExtra("pattern", gen.pattern);
                    i.putExtra("states", gen.state);
                    i.putExtra("input", gen.in);
                    i.putExtra("transition", gen.transition);
                    i.putExtra("initial", gen.initial);
                    i.putExtra("final", gen.fin);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(GenerateActivity.this,"Draw a Pattern",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

   /*public void click(View view)
    {

        if(patt.length()!=0) {
            myAutomata gen = new myAutomata(patt);
            gen.automata();
            Intent i = new Intent(GenerateActivity.this, Automata.class);
            i.putExtra("pattern", gen.pattern);
            i.putExtra("states", gen.state);
            i.putExtra("input", gen.in);
            i.putExtra("transition", gen.transition);
            i.putExtra("initial", gen.initial);
            i.putExtra("final", gen.fin);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Draw a Pattern",Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onStarted() {
        
    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        patt=PatternLockUtils.patternToString(p, pattern);

        Log.d(getClass().getName(), "Pattern complete: " +patt);
    }

    @Override
    public void onCleared() {
        patt="";

    }
}