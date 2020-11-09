package com.example.login_at1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.DocumentsContract;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BLOB;


public class Public_Dashboard_Fragment extends Fragment implements RecyclerViewAdapter.RecyclerViewClickListener {
    SQLiteDatabase database;
    Bitmap poster_image_bitmap;
    StringBuffer stringBuffer;
    LinearLayout public_dash_layout;
    List<Event> list_of_Events;
    RecyclerView myRV;
    RecyclerViewAdapter.RecyclerViewClickListener listener;
    final int MYREQUEST = 11;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_public__dashboard_, container, false);


        list_of_Events = new ArrayList<>();

        database = getActivity().getApplicationContext().openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);


        if (database != null) {
            //Toast.makeText(getActivity().getApplicationContext(), "DB is there", Toast.LENGTH_SHORT).show();

            // database.execSQL("CREATE TABLE IF NOT EXISTS event(event VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),image_byteArr BLOB);");

            database.execSQL("CREATE TABLE IF NOT EXISTS event(eventNo VARCHAR(20) primary key,event VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),image_byteArr BLOB);");

            database.execSQL("CREATE TABLE IF NOT EXISTS myEvent(username VARCHAR(20),position VARCHAR(20), primary key(username,position));");

            Cursor c = database.rawQuery("SELECT * FROM event", null);

            if (database != null) {
                //Toast.makeText(getActivity().getApplicationContext(), "DB is there", Toast.LENGTH_SHORT).show();
                if (c.moveToFirst()) {
                    if (c.getCount() == 0) {
                        Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                        return v;
                    }



                    list_of_Events.add(new Event(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
                    stringBuffer = new StringBuffer();
                    Date date1 = null;

                    while (c.moveToNext()) {
                        //Log.d()
                        list_of_Events.add(new Event(c.getString(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4)));

                    }
                    //Toast.makeText(getContext(), stringBuffer, Toast.LENGTH_SHORT).show();

                    SharedPreferences user=getContext().getSharedPreferences("EventNo",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=user.edit();
                    Log.d("List","List Size:"+Integer.toString(list_of_Events.size()));
                    edit.putString("number",Integer.toString(list_of_Events.size()));
                    edit.commit();

                    myRV = v.findViewById(R.id.recycler_view);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), list_of_Events, this);
                    myRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    myRV.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    myRV.setAdapter(adapter);

                }
            } else {
                TextView noEvents_textview = new TextView(getActivity());
                noEvents_textview.setText("No Events");
                noEvents_textview.setTextColor(getResources().getColor(android.R.color.white));
                noEvents_textview.setTextSize(Float.parseFloat("22dp"));
                public_dash_layout.setGravity(Gravity.CENTER);
                public_dash_layout.addView(noEvents_textview);
            }
            //poster_image_bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            //byte[] bitmapdata = blob.toByteArray();
        }

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(int position) {
        Log.d("MSG:","clicked "+list_of_Events.get(position).getNo());
        Intent i=new Intent(getActivity(),eventDetails.class);
        i.putExtra("position",list_of_Events.get(position).getNo());
        startActivityForResult(i,MYREQUEST);

    }
}