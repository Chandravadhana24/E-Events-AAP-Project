package com.example.login_at1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class eventDetails extends AppCompatActivity {

    TextView name,date,organization,genre;
    Button register;
    String num,ename,edate,eorg,egen;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        name=findViewById(R.id.event_name);
        date=findViewById(R.id.eventDate);
        organization=findViewById(R.id.eventOrganizer);
        genre=findViewById(R.id.eventGenre);
        register=findViewById(R.id.reg);
        Bundle b=getIntent().getExtras();
        num=b.getString("event");
        db=openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT * FROM event where eventNo="+num, null);

        ename=c.getString(1);
        edate=c.getString(2);
        eorg=c.getString(3);
        egen=c.getString(4);

        name.setText(ename);
        date.setText("Date: "+edate);
        organization.setText("Organized by "+eorg);
        genre.setText("Genre: "+egen);
    }
}