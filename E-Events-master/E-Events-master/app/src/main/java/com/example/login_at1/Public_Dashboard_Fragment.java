package com.example.login_at1;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Public_Dashboard_Fragment extends Fragment {
    SQLiteDatabase database;
    Bitmap poster_image_bitmap;
    StringBuffer stringBuffer;
    List<Event> list_of_Events;
    RecyclerView myRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_public__dashboard_, container, false);

        list_of_Events = new ArrayList<>();

            database = getActivity().getApplicationContext().openOrCreateDatabase("Events", Context.MODE_PRIVATE, null);
            if(database!=null) {
                //Toast.makeText(getActivity().getApplicationContext(), "DB is there", Toast.LENGTH_SHORT).show();
                Cursor c = database.rawQuery("SELECT * FROM event", null);
                if (c.getCount() == 0) {
                    Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                    return v;
                }

                stringBuffer = new StringBuffer();
                Date date1= null;

                while (c.moveToNext()) {

                    list_of_Events.add(new Event(c.getString(0),c.getString(1),c.getString(2),c.getString(3)));
                    /*stringBuffer.append("Event Name:" + c.getString(0) + "\n");
                    stringBuffer.append("Organization Name:" + c.getString(1) + "\n");
                    stringBuffer.append("Genre:" + c.getString(2) + "\n");
                    stringBuffer.append("Date:" + c.getString(3) + "\n");*/
                }
                //Toast.makeText(getContext(), stringBuffer, Toast.LENGTH_SHORT).show();
                myRV = v.findViewById(R.id.recycler_view);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(),list_of_Events);
                myRV.setLayoutManager(new GridLayoutManager(getActivity(),1));
                myRV.setAdapter(adapter);
            }
        //poster_image_bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        //byte[] bitmapdata = blob.toByteArray();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(stringBuffer!=null)
        Toast.makeText(getContext(), stringBuffer, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "String Null", Toast.LENGTH_SHORT).show();
    }
}