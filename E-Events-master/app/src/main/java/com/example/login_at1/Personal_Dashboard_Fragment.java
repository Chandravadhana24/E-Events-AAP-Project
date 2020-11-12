package com.example.login_at1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Personal_Dashboard_Fragment extends Fragment implements RecyclerViewAdapter.RecyclerViewClickListener {

    SQLiteDatabase database;
    Bitmap poster_image_bitmap;
    StringBuffer stringBuffer;
    LinearLayout public_dash_layout;
    List<Event> list_of_Events;
    RecyclerView myRV;
    RecyclerViewAdapter.RecyclerViewClickListener listener;
    final int MYREQUEST = 11;
    String uname;
    DatabaseHandlaer objectDatabaseHandler;
    ArrayList<modelClass> posters;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_personal__dashboard_, container, false);

        list_of_Events = new ArrayList<>();
        objectDatabaseHandler=new DatabaseHandlaer(getContext());
        posters=new ArrayList<>();
        posters=objectDatabaseHandler.getAllImageData();

        SharedPreferences userN=getActivity().getSharedPreferences("curruser", Context.MODE_PRIVATE);
        uname = userN.getString("username","NA");

        database = getActivity().getApplicationContext().openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);


        if (database != null) {
            //Toast.makeText(getActivity().getApplicationContext(), "DB is there", Toast.LENGTH_SHORT).show();

            // database.execSQL("CREATE TABLE IF NOT EXISTS event(event VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),image_byteArr BLOB);");

            database.execSQL("CREATE TABLE IF NOT EXISTS event(userName VARCHAR(20),eventNo VARCHAR(20) primary key,event_name VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),eventTime VARCHAR(20), eventLocation VARCHAR (100), image_byteArr BLOB);");

            database.execSQL("CREATE TABLE IF NOT EXISTS myEvent(username VARCHAR(20),position VARCHAR(20), primary key(username,position));");

            Cursor c = database.rawQuery("SELECT * FROM myEvent WHERE username='"+uname+"';", null);

            if (database != null) {
                //Toast.makeText(getActivity().getApplicationContext(), "DB is there", Toast.LENGTH_SHORT).show();
                if (c.moveToFirst()) {
                    if (c.getCount() == 0) {
                        Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        return v;
                    }

                    Cursor c1= database.rawQuery("SELECT * FROM event WHERE eventNo='"+c.getString(1)+"';",null);

                    if(c1.moveToFirst())
                    {
                        list_of_Events.add(new Event(c1.getString(0), c1.getString(1), c1.getString(2), c1.getString(3), c1.getString(4),c1.getString(5),c1.getString(6)));
                    }


                    while (c.moveToNext()) {
                        //Log.d()
                        c1= database.rawQuery("SELECT * FROM event WHERE eventNo='"+c.getString(1)+"';",null);
                        if(c1.moveToFirst())
                        {
                            list_of_Events.add(new Event(c1.getString(0), c1.getString(1), c1.getString(2), c1.getString(3), c1.getString(4),c1.getString(5),c1.getString(6)));
                        }

                    }
                    //Toast.makeText(getContext(), stringBuffer, Toast.LENGTH_SHORT).show();

                    SharedPreferences user=getContext().getSharedPreferences("EventNo",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=user.edit();
                    Log.d("List","List Size:"+Integer.toString(list_of_Events.size()));
                    edit.putString("number",Integer.toString(list_of_Events.size()));
                    edit.commit();

                    myRV = v.findViewById(R.id.recycler_view);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), posters, list_of_Events, this);
                    myRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    myRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    myRV.setAdapter(adapter);

                }
            }
            //poster_image_bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            //byte[] bitmapdata = blob.toByteArray();
        }


        return v;
    }

    @Override
    public void onClick(int position) {
        Intent i=new Intent(getActivity(),eventDetails.class);
        i.putExtra("position",list_of_Events.get(position).getNo());
        startActivityForResult(i,MYREQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MYREQUEST)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new my_event_frag()).commit();

        }
    }
}