package com.example.login_at1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view =inflater.inflate(R.layout.fragment_all_event_frag, container, false);

        SharedPreferences userN=getActivity().getSharedPreferences("curruser", Context.MODE_PRIVATE);
        String p=userN.getString("username","NA");
        Log.d("username:",p);
        tv=view.findViewById(R.id.username);
        tv.setText(p);
        return view;
    }



}