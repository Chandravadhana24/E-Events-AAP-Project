package com.example.login_at1;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.sql.Time;
class event{
    String name,organisation,type;
    Date start_date,end_date;
    Time start_time,end_time;

    private event(String name, String org, String type, Date st_date, Date end_date, Time st_time, Time end_time){
        this.name = name;
        organisation = org;
        this.type = type;
        start_date = st_date;
        this.end_date = end_date;
        start_time = st_time;
        this.end_time = end_time;
    }

}
public class Public_Dashboard_Fragment extends Fragment {
    event Obj;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_public__dashboard_, container, false);
        Bundle allInfoBundle;

        return v;
    }

}