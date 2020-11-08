package com.example.login_at1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class QuestionActivity extends AppCompatActivity {
    EditText ph, text;
    Button smsMessage,backq;
    //String num = "3383687";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ph = (EditText)findViewById(R.id.no);
        text = (EditText)findViewById(R.id.msg);
        smsMessage = (Button)findViewById(R.id.sms);
        backq = (Button)findViewById(R.id.backq);
        ph.setText("338-3687");
       // smsMessage.setOnClickListener(this);

        smsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = ph.getText().toString();
                String msg = text.getText().toString();
                Intent intent=new Intent(getApplicationContext(),QuestionActivity.class);
                SmsManager sms=SmsManager.getDefault();
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QuestionActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            111);
                }
                sms.sendTextMessage(no, null, msg, null,null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                text.setText("");
            }
        });

        backq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intt = new Intent(QuestionActivity.this, SettingsActivity.class);
                startActivity(intt);
            }
        });

    }

}