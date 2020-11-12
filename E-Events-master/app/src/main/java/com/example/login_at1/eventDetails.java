package com.example.login_at1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class eventDetails extends AppCompatActivity  implements View.OnClickListener {

    TextView name,date,organization,genre,time,venue;
    ImageView img;
    Button register;
    String num,uname,username,ename="",edate="",etime="",eorg="",egen="",elocation="";
    SQLiteDatabase db;
    private NotificationHelper mNotificationHelper;
    DatabaseHandlaer objectDatabaseHandler;
    int no;
    ArrayList<modelClass> posters;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        SharedPreferences userN=getSharedPreferences("curruser", Context.MODE_PRIVATE);
        uname = userN.getString("username","NA");

        objectDatabaseHandler=new DatabaseHandlaer(this);
        posters=new ArrayList<>();
        posters=objectDatabaseHandler.getAllImageData();



        img=findViewById(R.id.poster);
        name=(TextView)findViewById(R.id.eventName);
        date=(TextView)findViewById(R.id.eventDate);
        time = findViewById(R.id.eventTime);
        organization=(TextView)findViewById(R.id.eventOrganizer);
        genre=(TextView)findViewById(R.id.eventGenre);
        register=(Button)findViewById(R.id.reg);
        mNotificationHelper = new NotificationHelper(eventDetails.this);
        venue=findViewById(R.id.eventLocation);

        Bundle b=getIntent().getExtras();
        num=b.getString("position");
        Log.d("pos","clicked position:"+num);
        db=openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);

        no=Integer.parseInt(num);

        if(db!= null)
        {
            Cursor c = db.rawQuery("SELECT userName,eventNo,event_name,organization,genre,eventDate,eventTime,eventLocation FROM event WHERE eventNo='"+num.toString()+"';", null);

            if(c.moveToFirst())
            {
                img.setImageBitmap(posters.get(no-1).getImage());
                username=c.getString(0);
                ename=c.getString(2);
                edate=c.getString(5);
                eorg=c.getString(3);
                egen=c.getString(4);
                etime=c.getString(6);
                elocation=c.getString(7);
            }


            name.setText(ename);
            date.setText("Date: "+edate);
            time.setText("Start Time: " + etime);
            organization.setText("Organized by "+eorg);
            genre.setText("Genre: "+egen);
            venue.setText("Location: "+elocation);

            Cursor c1 = db.rawQuery("SELECT username,position FROM myEvent where position='"+num+"' and username='"+uname+"';",null);

            if(username.equalsIgnoreCase(uname))
            {
                register.setText("Modify Event");
            }
            else if(c1.moveToFirst())
            {
                register.setText("Un-Register");
            }
            else
            {
                register.setText("Register");
            }

            register.setOnClickListener(this);



            Log.d("Details","Name:"+ename+"\nDate:"+edate+ "\nTime:" +etime + "\norg:"+eorg+"\ngen:"+egen);
        }

    }

    @Override
    public void onClick(View view) {

        if(register.getText().toString().equals("Modify Event"))
        {
            Intent i=new Intent(this,modifyEvents.class);
            i.putExtra("position",num);
            startActivity(i);
        }
        if(register.getText().toString().equals("Un-Register"))
        {
            Cursor c1 = db.rawQuery("SELECT username,position FROM myEvent where position='"+num+"' and username='"+uname+"';",null);

            if(c1.moveToFirst())
            {
                db.execSQL("DELETE FROM myEvent where position='"+num+"' and username='"+uname+"';");
                Toast.makeText(eventDetails.this, "You are no longer registered to this event", Toast.LENGTH_SHORT).show();
                register.setText("Register");
            }
        }
        else if(register.getText().toString().equals("Register"))
        {
            db.execSQL("INSERT INTO myEvent VALUES ('"+uname+"','"+num+"');");
            Toast.makeText(eventDetails.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            register.setText("Un-Register");
            sendNotif_onChannel("E-Events","Registered Successfully for " + ename);
        }
    }


    public void sendNotif_onChannel(String title,String msg)
    {
        NotificationCompat.Builder mBuilder = mNotificationHelper.getChannelNotif(title,msg);
        mNotificationHelper.getManager().notify(1,mBuilder.build());
    }
}