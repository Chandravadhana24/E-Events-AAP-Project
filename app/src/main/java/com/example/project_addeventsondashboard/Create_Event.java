package com.example.project_addeventsondashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Create_Event extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText event_name, organisation_name,date_editText;
    Button create;
    ImageButton poster_imageButton;
    Spinner genre_spinner;

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

            }
        });
        //Toast.makeText(Create_Event.this,event_name.getText().toString(),Toast.LENGTH_LONG).show();

        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Create_Btn",date_editText.getText().toString());
                if((event_name.getText().toString().length()!=0)&&(organisation_name.getText().toString().length()!=0)&&(date_editText.getText().toString().length()!=0))
                {
                    create.setEnabled(true);
                    create.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    create.setTextColor(getResources().getColor(android.R.color.white));
                    Toast.makeText(Create_Event.this,"Registration successful.",Toast.LENGTH_SHORT).show();
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
                /*Intent myIntent = new Intent(Create_Event.this,)*/
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
}