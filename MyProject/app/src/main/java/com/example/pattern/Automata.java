package com.example.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Automata extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automata);

        Bundle b=getIntent().getExtras();
        String pattern=b.getString("pattern");
        String state=b.getString("states");
        String in=b.getString("input");
        String transition=b.getString("transition");
        String initial=b.getString("initial");
        String fin=b.getString("final");
        tv=findViewById(R.id.Transition_Function);
        tv.setText("");
        tv.setText(state+"\n\n"+in+"\n\n"+transition+"\n"+initial+"\n\n"+fin+"\n\n");

    }
}