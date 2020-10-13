package com.example.project_addeventsondashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class Create_Event extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText event_name, organisation_name,date_editText;
    Button create;
    ImageButton poster_imageButton;
    Spinner genre_spinner;
    int flag;
    private static final int CAMERA_REQUEST = 123;
    public class event{
        String name,organisation,type;
        Date start_date,end_date;
        Time start_time,end_time;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

        event_name = findViewById(R.id.event_name);
        organisation_name=findViewById(R.id.org_name);
        genre_spinner = findViewById(R.id.genre_spinner);
        poster_imageButton = findViewById(R.id.poster_image);
        create = findViewById(R.id.createEvent_Button);
        date_editText = findViewById(R.id.date_editText);

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
                Intent i = new Intent();
               Intent myIntent = new Intent(Create_Event.this,all_event_frag.class);
               myIntent.putExtra("name",event_name.getText().toString());
                myIntent.putExtra("organisation",organisation_name.getText().toString());
                myIntent.putExtra("type",genre_spinner.getSelectedItem().toString());
                Toast.makeText(Create_Event.this,genre_spinner.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                myIntent.putExtra("start date",date_editText.getText().toString());
                myIntent.putExtra("poster",convertBitMap_to_byteArray(poster_imageButton.getDrawingCache()));
               startActivity(myIntent);
               setResult(RESULT_OK,i);
               //Toast.makeText(Create_Event.this,"I am clickable",Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String whichGenre = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(this,whichGenre,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 24 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            ImageView imageView = (ImageView) findViewById(R.id.poster_image);
            imageView.setImageURI(imageUri);
        }
        if (requestCode == 123 && resultCode == RESULT_OK)
        {
            Bitmap myCamPic = (Bitmap) data.getExtras().get("data");
            poster_imageButton.setImageBitmap(myCamPic);
        }
    }

    private byte[] convertBitMap_to_byteArray(Bitmap _bitmap)
    {
        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
        _bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
        return  _bs.toByteArray();
    }
    }