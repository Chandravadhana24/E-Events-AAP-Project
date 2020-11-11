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
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class modifyEvents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    Button mevent;
    EditText event_name, organisation_name,date_editText,time_editText,venue_editText;
    ImageView poster_imageButton;
    Spinner genre_spinner;
    int flag;
    Byte[] image_byteArray;
    SQLiteDatabase db;
    String whichGenre,whichOption;
    private static final int CAMERA_REQUEST = 123;
    static int no;
    Uri imageFilePath;
    Bitmap imageToStore;
    DatabaseHandlaer objectDatabaseHandler;
    String uname,num;
    ArrayList<modelClass> posters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_events);

        Bundle b=getIntent().getExtras();
        num=b.getString("position");

        no=Integer.parseInt(num);
        s1=findViewById(R.id.spinner1);
        ArrayAdapter adap1= ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_item);
        adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adap1);
        s1.setOnItemSelectedListener(this);

        mevent=findViewById(R.id.modifyEvent_Button);
        event_name = findViewById(R.id.event_name);
        organisation_name=findViewById(R.id.org_name);
        genre_spinner = findViewById(R.id.genre_spinner);
        poster_imageButton = findViewById(R.id.poster_image);
        date_editText = findViewById(R.id.date_editText);
        time_editText = findViewById(R.id.time_editText);
        venue_editText = findViewById(R.id.venue_editText);
        objectDatabaseHandler=new DatabaseHandlaer(this);
        SharedPreferences userN=getSharedPreferences("EventNo", Context.MODE_PRIVATE);
        String p=userN.getString("number","0");

        SharedPreferences userName=getSharedPreferences("curruser", Context.MODE_PRIVATE);
        uname=userName.getString("username","NA");

        Log.d("size","Preference size:"+p);

        posters=objectDatabaseHandler.getAllImageData();

        db=openOrCreateDatabase("Events",MODE_PRIVATE,null);

        if(db!= null) {
            Cursor c = db.rawQuery("SELECT userName,eventNo,event_name,organization,genre,eventDate,eventTime FROM event WHERE eventNo='" + num.toString() + "';", null);

            if (c.moveToFirst()) {
                poster_imageButton.setImageBitmap(posters.get(no - 1).getImage());

                event_name.setText(c.getString(2));
                date_editText.setText(c.getString(5));
                organisation_name.setText(c.getString(3));
                ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this, R.array.Genre, android.R.layout.simple_spinner_item);
                genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genre_spinner.setAdapter(genreAdapter);
                int spinPod=genreAdapter.getPosition(c.getString(4));
                genre_spinner.setSelection(spinPod);
                genre_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        whichGenre = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                time_editText.setText( c.getString(6));
            }
        }


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

                DatePickerDialog datePickerDialog = new DatePickerDialog(modifyEvents.this,myDatePickerListener,year,month,day);
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(modifyEvents.this,myTimePickerListener,mHour,mMin,false);
                timePickerDialog.show();


            }
        });
        venue_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(modifyEvents.this,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //if(menuItem.getItemId()==R.id.item1_locate)
                        // {
                        AlertDialog.Builder builder = new AlertDialog.Builder(modifyEvents.this);
                        EditText inputLocation = new EditText(modifyEvents.this);
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



        poster_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(modifyEvents.this);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(modifyEvents.this,R.array.Choose_photo_from,android.R.layout.simple_list_item_1);
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
                    mevent.setEnabled(true);
                    mevent.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                    mevent.setTextColor(getResources().getColor(android.R.color.white));
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

        mevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichOption.equalsIgnoreCase("update"))
                {
                    if(db!=null)
                    {
                        if(poster_imageButton.getDrawable()!=null)
                        {
                            objectDatabaseHandler.updateImage(new modelClass(Integer.toString(no),imageToStore));
                            Log.d("pic","inside if");
                        }

                        db.execSQL("UPDATE event set  event_name='"+event_name.getText().toString()+"' WHERE eventNo='"+num+"';");
                        db.execSQL("UPDATE event set  organization='"+organisation_name.getText().toString()+"' WHERE eventNo='"+num+"';");
                        db.execSQL("UPDATE event set  genre='"+whichGenre+"' WHERE eventNo='"+num+"';");
                        db.execSQL("UPDATE event set  eventDate='"+date_editText.getText().toString()+"' WHERE eventNo='"+num+"';");
                        db.execSQL("UPDATE event set  eventTime='"+time_editText.getText().toString()+"' WHERE eventNo='"+num+"';");

                    }
                }
                else if (whichOption.equalsIgnoreCase("delete"))
                {
                    objectDatabaseHandler.deleteImage(new modelClass(num,imageToStore));
                    db.execSQL("DELETE FROM event WHERE eventNo='"+num+"';");
                    db.execSQL("DELETE FROM myEvent WHERE position='"+num+"';");
                    Cursor c=db.rawQuery("SELECT eventNo FROM event WHERE eventNo>'"+num+"'",null);
                    if(c.moveToFirst())
                    {
                        int eno;
                        eno=Integer.parseInt(c.getString(0));
                        eno-=1;

                        db.execSQL("UPDATE event set eventNo='"+Integer.toString(eno)+"' WHERE eventNo='"+c.getString(0)+"';");
                        objectDatabaseHandler.deleteImage(new modelClass(num,imageToStore));

                        while(c.moveToNext())
                        {
                            eno=Integer.parseInt(c.getString(0));
                            eno-=1;

                            db.execSQL("UPDATE event set eventNo='"+Integer.toString(eno)+"' WHERE eventNo='"+c.getString(0)+"';");
                        }
                    }

                    c=db.rawQuery("SELECT position FROM myEvent WHERE position>'"+num+"'",null);

                    if(c.moveToFirst())
                    {
                        int eno;
                        eno=Integer.parseInt(c.getString(0));
                        eno-=1;

                        db.execSQL("UPDATE myEvent set position='"+Integer.toString(eno)+"' WHERE position='"+c.getString(0)+"';");

                        while(c.moveToNext())
                        {
                            eno=Integer.parseInt(c.getString(0));
                            eno-=1;

                            db.execSQL("UPDATE myEvent set position='"+Integer.toString(eno)+"' WHERE position='"+c.getString(0)+"';");
                        }
                    }
                    SharedPreferences user=getSharedPreferences("EventNo",Context.MODE_PRIVATE);
                    int n= Integer.parseInt(user.getString("number","1"));
                    SharedPreferences.Editor edit=user.edit();
                    edit.putString("number",Integer.toString(n-1));
                    edit.commit();
                    Log.d("delete","event deleted");
                }
                else {
                    Cursor c = db.rawQuery("SELECT * FROM myEvent WHERE position='" + num + "'", null);
                    // Checking if no records found 
                    if (c.getCount() == 0) {
                        showMessage("Error", "No records found");
                        return;
                    }
                    // Appending records to a string buffer 
                    StringBuffer buffer = new StringBuffer();
                    while (c.moveToNext()) {

                        buffer.append("Name: " + c.getString(0) + "\n");
                    }
                    // Displaying all records 
                    showMessage("Participant Details", buffer.toString());
                }




                Intent i=new Intent(modifyEvents.this,nav_drawer.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        whichOption = adapterView.getItemAtPosition(i).toString();
        mevent.setEnabled(true);
        mevent.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        mevent.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        whichOption=null;
        mevent.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 24 && resultCode == RESULT_OK && data != null && data.getData() != null) {


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

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
        return;
    }

}