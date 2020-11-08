package com.example.login_at1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class eventDetails extends AppCompatActivity {

    TextView name,date,organization,genre;
    Button register;
    String num,ename="",edate="",eorg="",egen="";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        name=(TextView)findViewById(R.id.eventName);
        date=(TextView)findViewById(R.id.eventDate);
        organization=(TextView)findViewById(R.id.eventOrganizer);
        genre=(TextView)findViewById(R.id.eventGenre);
        register=(Button)findViewById(R.id.reg);
        Bundle b=getIntent().getExtras();
        num=b.getString("position");
        Log.d("pos","clicked position:"+num);
        db=openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);

        if(db!= null)
        {
            Cursor c = db.rawQuery("SELECT eventNo,event,organization,genre,eventDate FROM event WHERE eventNo='"+num.toString()+"';", null);

            if(c.moveToFirst())
            {
                ename=c.getString(1);
                edate=c.getString(4);
                eorg=c.getString(2);
                egen=c.getString(3);
            }


            name.setText(ename);
            date.setText("Date: "+edate);
            organization.setText("Organized by "+eorg);
            genre.setText("Genre: "+egen);

            Log.d("Details","Name:"+ename+"\nDate:"+edate+"\norg:"+eorg+"\ngen:"+egen);
        }

    }
}