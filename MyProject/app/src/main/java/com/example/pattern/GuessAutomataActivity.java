package com.example.pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GuessAutomataActivity extends AppCompatActivity {

    TextView tv;
    Button b;
    int n=1;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_automata);

        tv=findViewById(R.id.guessTransition);
        b=findViewById(R.id.drawGuessPattern);
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Pattern");

        Query check=reference.

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String patt=snapshot.child(Integer.toString(n)).child("pattern").getValue(String.class);
                    myAutomata p=new myAutomata(patt);
                    p.automata();
                    tv.setText("");
                    tv.setText(p.state+"\n\n"+p.in+"\n\n"+p.transition+"\n"+p.initial+"\n\n"+p.fin+"\n\n");
                }
                else
                {
                    Log.d("msg","Snapshot do not exist ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}