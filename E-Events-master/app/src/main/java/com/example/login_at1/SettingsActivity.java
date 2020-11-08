package com.example.login_at1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    ListView list;
    String[] web = {
            "Notifications",
            "Check Us Out",
            "Ask a Question",
            "Help",
            "Call Us"
    } ;
    Integer[] imageId = {
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        CustomList adapter = new
                CustomList(SettingsActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(id==0){
                    Toast.makeText(getApplicationContext(), web[+ position], Toast.LENGTH_SHORT).show();
                }

                if(id==1){
                    Intent ind = new Intent(SettingsActivity.this, Contact.class);
                    startActivity(ind);
                    Toast.makeText(getApplicationContext(), web[+ position], Toast.LENGTH_SHORT).show();
                }

                if(id==2){
                    Intent in = new Intent(SettingsActivity.this, QuestionActivity.class);
                    startActivity(in);
                    Toast.makeText(getApplicationContext(), web[+ position], Toast.LENGTH_SHORT).show();
                }

                if(id==3){
                    Intent i = new Intent(SettingsActivity.this, HelpActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), web[+ position], Toast.LENGTH_SHORT).show();
                }
                if(id==4){
                    String ph_no = "E-Events";
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + ph_no));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                123);
                        return;
                    }
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), web[+ position], Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}