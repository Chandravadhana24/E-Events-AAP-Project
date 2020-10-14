package com.example.login_at1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class all_event_frag extends Fragment {

    communicator comm;
    TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        if(comm==null)
            comm=(communicator)getActivity();
        comm.sendAllEvents();


        return inflater.inflate(R.layout.fragment_all_event_frag, container, false);
    }


    public void changeData(String name) {

    }
}