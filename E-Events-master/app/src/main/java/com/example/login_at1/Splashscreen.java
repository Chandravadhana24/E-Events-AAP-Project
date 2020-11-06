package com.example.login_at1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;

    //variables
    Animation topAnim,bottomAnim;
    ImageView sym;
    TextView tv;
    SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        db = openOrCreateDatabase("Events", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(username VARCHAR, name VARCHAR, password VARCHAR,email_id VARCHAR);");
        c = db.rawQuery("SELECT * FROM users",null);
        if(c.moveToFirst())
        {

        }
        //hooks
        sym=findViewById(R.id.symbol);
        tv=findViewById(R.id.logo);

        //assign animations
        sym.setAnimation(topAnim);
        tv.setAnimation(bottomAnim);

        //to the next activity choice
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(c.moveToFirst())
                {
                    Intent i1 = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(i1);
                    finish();
                }
                else {
                    Intent i2 = new Intent(Splashscreen.this, CreateAccountActivity.class);
                    startActivity(i2);
                    finish();
                }
            }
        },SPLASH_SCREEN);

    }
}