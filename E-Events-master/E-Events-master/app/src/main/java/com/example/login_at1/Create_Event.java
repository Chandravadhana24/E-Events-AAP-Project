package com.example.login_at1;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create_Event extends AppCompatActivity implements View.OnClickListener, ExampleBottomSheetDialog.BottomSheetListener
{

    EditText name, username, email, password;
    Button createaccount;
    TextView result, rememberme;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ImageView importimage;
    Button importimagebutton;
    final int CAMERA_REQUEST=18;
    SQLiteDatabase dbase;
    String passwordregex ="^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&-+=()])(?=\\S+$).{8,20}$";
    String usernameregex = "^[aA-zZ]\\w{5,29}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.Theme_AppCompat_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        result=findViewById(R.id.result);
        rememberme=(TextView)findViewById(R.id.rememberme);
        rememberme.setPaintFlags(rememberme.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        dbase = openOrCreateDatabase("E-Events", Context.MODE_PRIVATE,null);
        dbase.execSQL("CREATE TABLE IF NOT EXISTS users(username VARCHAR, name VARCHAR, password VARCHAR,email_id VARCHAR);");

        rememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sf=getSharedPreferences("rememberuser", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sf.edit();
                edit.clear(); // remove existing entries
                edit.putString("username",username.getText().toString());
                edit.putString("password",password.getText().toString());
                edit.commit();
                Intent i=new Intent(Create_Event.this,nav_drawer.class);
                i.putExtra("username",username.getText().toString());
                startActivity(i);

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try
                {
                    if(!(username.getText().toString().trim().matches(usernameregex)))
                    {
                        username.setError("Invalid username");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if(!(email.getText().toString().trim().matches(emailPattern)))
                    {
                        email.setError("invalid email");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if(!isValidPassword(password.getText().toString()))
                    {
                        password.setError("invalid password");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


        createaccount = findViewById(R.id.createaccount);
        createaccount.setOnClickListener(this);

        importimagebutton = findViewById(R.id.importimagebutton);

        importimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating an instance of the class we created (Examplebottomsheetdialog)

                ExampleBottomSheetDialog bottomSheet= new ExampleBottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "dpbottomsheet");
            }
        });

        importimage = (ImageView)findViewById(R.id.importimage);

    }



    public static boolean
    isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.[a-z])(?=.[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public boolean isValidUsername(final String username)
    {
        Pattern pattern;
        Matcher matcher;
        final String usernameregex = "^[aA-zZ]\\w{5,29}$";
        pattern = Pattern.compile(usernameregex);
        matcher = pattern.matcher(username);
        return matcher.matches();

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.createaccount) {


            if (result.getText() == "All Valid")
            {
                result.setText("Your account has been created!");
                dbase.execSQL("INSERT INTO users VALUES('" + username.getText().toString() + "','" + name.getText().toString() + "','" + password.getText().toString() + "','" + email.getText().toString() + "');");
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            }
            else
                result.setText("Please fill in appropriate details");


        }


        /*if (view.getId() == R.id.importimagebutton) {

            Toast.makeText(getApplicationContext(),"imageview clicked",Toast.LENGTH_SHORT).show();
            Log.d("insde","insideimportimage");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
            // Setting Dialog Title
            alertDialog.setTitle("Import image via");
            // Setting Dialog Message
            alertDialog.setMessage("Capture an image or choose from your gallery?");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.image);
            // Setting Positive "Yes" Button

            alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    //super.onActivityResult(requestCode,resultCode,data);
                    if (requestCode == CAMERA_REQUEST) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        importimage.setImageBitmap(photo);
                        //Toast.makeText(this,String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
                    }
                }

            });

            alertDialog.show();

        }*/
    }


    @Override
    public void OnButtonClicked(Uri imageuri) {
        //Log.d("working", "entered main activity's onButtonVlicked");
        //Toast.makeText(this, "entered main activity's onButtonVlicked", Toast.LENGTH_SHORT).show();
        importimage.setImageURI(imageuri);
    }
    @Override
    public void OnButtonClicked(Bitmap photo) {

        importimage.setImageBitmap(photo);
    }

}