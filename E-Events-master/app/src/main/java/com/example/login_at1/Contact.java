package com.example.login_at1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Contact extends AppCompatActivity {

    ImageButton share, link;
    Button backC;
    String links = "www.indiatoday.in/sports/tennis/story/rafael-nadal-italian-open-2nd-round-win-french-open-warm-up-tsitsipas-stunned-1722666-2020-09-17";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        share = (ImageButton)findViewById(R.id.share);
        link = (ImageButton)findViewById(R.id.link);
        backC = (Button)findViewById(R.id.backC);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, links);
                sendIntent.setType("text/plain");
                // Always use string resources for UI text.
                String title = getResources().getString(R.string.chooser_title);
                Intent shareIntent = Intent.createChooser(sendIntent, title);
                startActivity(shareIntent);
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+links));
                startActivity(i);
            }
        });

        backC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ind = new Intent(Contact.this, SettingsActivity.class);
                startActivity(ind);
            }
        });
    }
}