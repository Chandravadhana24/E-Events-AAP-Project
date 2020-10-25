package com.example.login_at1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class nav_drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    final int MY_REQ_CODE = 2411;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new all_event_frag()).commit();

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.myEvents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new my_event_frag()).commit();
                break;

            case R.id.allEvents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new all_event_frag()).commit();
                break;

            case R.id.logout:
                Intent i=new Intent(nav_drawer.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }
    public void Add_Event(View view) {
        Intent myContents = new Intent(this,Create_Event.class);
        startActivityForResult(myContents,MY_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQ_CODE)
        {
            Bundle allInfoBundle = getIntent().getExtras(); // getActivity() added
            if(allInfoBundle!=null) {
                Toast.makeText(this, allInfoBundle.getString("name") + allInfoBundle.getString("organisation") + allInfoBundle.getString("type") + allInfoBundle.getString("start date"), Toast.LENGTH_LONG).show();
                Log.d("MainActivity", allInfoBundle.getString("name") + allInfoBundle.getString("organisation") + allInfoBundle.getString("type") + allInfoBundle.getString("start date"));
            }
        }

    }
}