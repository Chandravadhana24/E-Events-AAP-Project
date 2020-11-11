package com.example.login_at1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

public class Create_Event extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText event_name, organisation_name,date_editText,venue_editText;
    Button create;
    ImageButton poster_imageButton, venue_imagebutton;
    Spinner genre_spinner;
    int flag;
    byte[] image_byteArray;
    SQLiteDatabase db;
    String whichGenre;
    LocationManager locationManager;
    private static final int CAMERA_REQUEST = 123;
    static int no;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

        event_name = findViewById(R.id.event_name);
        organisation_name=findViewById(R.id.org_name);
        genre_spinner = findViewById(R.id.genre_spinner);
        poster_imageButton = findViewById(R.id.poster_image);
        create = findViewById(R.id.createEvent_Button);
        date_editText = findViewById(R.id.date_editText);
        venue_editText = findViewById(R.id.venue_editText);
        venue_imagebutton=findViewById(R.id.venue_imagebutton);


        SharedPreferences userN=getSharedPreferences("EventNo", Context.MODE_PRIVATE);
        String p=userN.getString("number","0");

        Log.d("size","Preference size:"+p);

        no= Integer.parseInt(p);

        db=openOrCreateDatabase("Events",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS event(eventNo VARCHAR(20),event VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),image_byteArr BLOB);");

        date_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar myCalendar = Calendar.getInstance();
                int day, month, year;
                day = myCalendar.get(Calendar.DAY_OF_MONTH);
                month = myCalendar.get(Calendar.MONTH);
                year = myCalendar.get(Calendar.YEAR);
                DatePickerDialog.OnDateSetListener myDatePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date_editText.setText(i2 + "/" + (i1+1) + "/" + i);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(Create_Event.this,myDatePickerListener,year,month,day);
                datePickerDialog.show();
            }
        });
        //----------------------------------------------------------------------

        venue_imagebutton = (ImageButton)findViewById(R.id.venue_imagebutton);
        venue_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPointOnMap();
            }
        });

        //------------------------------------------------------
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this, R.array.Genre, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre_spinner.setAdapter(genreAdapter);
        genre_spinner.setOnItemSelectedListener(this);

        poster_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Create_Event.this);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Create_Event.this,R.array.Choose_photo_from,android.R.layout.simple_list_item_1);
                builder.setTitle("Choose photo from");
                builder.setAdapter(adapter, new Dialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strName = (String) adapter.getItem(i);
                        Log.d("Selection",strName);
                        if(strName.equalsIgnoreCase("Gallery"))
                        {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent,24);
                            return;
                        }
                        if (strName.equalsIgnoreCase("Camera"))
                        {
                            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent,CAMERA_REQUEST);
                        }
                    }
                });
                builder.show();
            }
        });
        //Toast.makeText(Create_Event.this,event_name.getText().toString(),Toast.LENGTH_LONG).show();
        flag=0; //EditText not touched yet
        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                flag=1;
                Log.d("Create_Btn",date_editText.getText().toString());
                if((event_name.getText().toString().length()!=0)&&(organisation_name.getText().toString().length()!=0)&&(date_editText.getText().toString().length()!=0))
                {
                    create.setEnabled(true);
                    create.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    create.setTextColor(getResources().getColor(android.R.color.white));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        event_name.addTextChangedListener(myTextWatcher);
        organisation_name.addTextChangedListener(myTextWatcher);
        date_editText.addTextChangedListener(myTextWatcher);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no++;
                db.execSQL("INSERT INTO event VALUES('"+Integer.toString(no) + "','"+event_name.getText().toString()+"','"+organisation_name.getText().toString()+"','"+whichGenre+"','"+date_editText.getText().toString() + "','" + image_byteArray +"');");

                Toast.makeText(Create_Event.this,"Data entered",Toast.LENGTH_SHORT ).show();

                Intent i = new Intent();
                Intent myIntent = new Intent(Create_Event.this,nav_drawer.class);

                startActivity(myIntent);

            }
        });
    }

    //venuelocation
    static final int PICK_MAP_POINT_REQUEST = 999;  // The request code
    private void pickPointOnMap() {
        Intent pickPointIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(pickPointIntent, PICK_MAP_POINT_REQUEST);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        whichGenre = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MAP_POINT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");
                venue_editText.setText((float) latLng.latitude +" "+ (float)latLng.longitude);
                //Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == 24 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            ImageView imageView = findViewById(R.id.poster_image);
            imageView.setImageURI(imageUri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int size = bitmap.getRowBytes() * bitmap.getHeight();
            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
            bitmap.copyPixelsToBuffer(byteBuffer);
            image_byteArray = byteBuffer.array();
            Log.d("IS image arr null",Boolean.toString(image_byteArray==null));
        }
        if (requestCode == 123 && resultCode == RESULT_OK)
        {
            Bitmap myCamPic = (Bitmap) data.getExtras().get("data");
            poster_imageButton.setImageBitmap(myCamPic);
            int width = myCamPic.getWidth();
            int height = myCamPic.getHeight();

            int size = myCamPic.getRowBytes() * myCamPic.getHeight();
            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
            myCamPic.copyPixelsToBuffer(byteBuffer);
            image_byteArray = byteBuffer.array();
            Log.d("Create_Event",Boolean.toString(image_byteArray==null));
        }
    }



}