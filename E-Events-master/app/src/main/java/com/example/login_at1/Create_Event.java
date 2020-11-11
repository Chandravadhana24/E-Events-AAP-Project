package com.example.login_at1;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class Create_Event extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText event_name, organisation_name,date_editText,time_editText,venue_editText;
    Button create;
    ImageView poster_imageButton;
    Spinner genre_spinner;
    int flag;
    Byte[] image_byteArray;
    SQLiteDatabase db;
    String whichGenre;
    private static final int CAMERA_REQUEST = 123;
    static int no;
    Uri imageFilePath;
    Bitmap imageToStore;
    DatabaseHandlaer objectDatabaseHandler;


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
        time_editText = findViewById(R.id.time_editText);
        venue_editText = findViewById(R.id.venue_editText);
        objectDatabaseHandler=new DatabaseHandlaer(this);

        SharedPreferences userN=getSharedPreferences("EventNo", Context.MODE_PRIVATE);
        String p=userN.getString("number","0");

        Log.d("size","Preference size:"+p);

        no= Integer.parseInt(p);
        no++;

        db=openOrCreateDatabase("Events",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS event(eventNo VARCHAR(20),event_name VARCHAR(20),organization VARCHAR(20),genre VARCHAR(20),eventDate VARCHAR(20),eventTime VARCHAR(20));");

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

        time_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mHour,mMin;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener myTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if(i>12)
                            time_editText.setText(i-12 + ":" + i1 +"pm");
                        else
                            time_editText.setText(i + ":" + i1 + "am");
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(Create_Event.this,myTimePickerListener,mHour,mMin,false);
                timePickerDialog.show();


            }
        });
        venue_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Create_Event.this,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //if(menuItem.getItemId()==R.id.item1_locate)
                       // {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Create_Event.this);
                            EditText inputLocation = new EditText(Create_Event.this);
                            builder.setView(inputLocation);
                            builder.show();

                            String location_url = inputLocation.getText().toString();
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location_url));
                            //startActivity(mapIntent);
                       // }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu_for_venue);
                popupMenu.show();
                //Intent mapIntent = new Intent(Intent.ACTION_VIEW,"geo")
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
                if((event_name.getText().toString().length()!=0)&&(organisation_name.getText().toString().length()!=0)&&(date_editText.getText().toString().length()!=0)&&(time_editText.getText().toString().length()!=0))
                {
                    create.setEnabled(true);
                    create.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
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
        time_editText.addTextChangedListener(myTextWatcher);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(poster_imageButton.getDrawable()!=null)
                {
                    objectDatabaseHandler.storeImage(new modelClass(Integer.toString(no),imageToStore));
                    Log.d("pic","inside if");
                }

                db.execSQL("INSERT INTO event VALUES('"+ no + "'," +
                        "'"+event_name.getText().toString()+"'," +
                        "'"+organisation_name.getText().toString()+"'," +
                        "'"+whichGenre+"'," +
                        "'"+date_editText.getText().toString() + "'," +
                        "'" + time_editText.getText().toString() + "');");

                Toast.makeText(Create_Event.this,"Event created",Toast.LENGTH_SHORT ).show();
                Intent myIntent = new Intent(Create_Event.this,nav_drawer.class);

                startActivity(myIntent);

            }
        });
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

        if (requestCode == 24 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            /*Uri imageUri = data.getData();
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
            Log.d("IS image arr null",Boolean.toString(image_byteArray==null));*/

            imageFilePath= data.getData();
            try {
                imageToStore=MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                poster_imageButton.setImageBitmap(imageToStore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 123 && resultCode == RESULT_OK)
        {
            imageToStore= (Bitmap) data.getExtras().get("data");

            poster_imageButton.setImageBitmap(imageToStore);

        }
    }

    public void storeImage(View view)
    {

    }


}