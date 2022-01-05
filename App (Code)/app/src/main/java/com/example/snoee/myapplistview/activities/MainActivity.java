package com.example.snoee.myapplistview.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.snoee.myapplistview.AutoCutDialogFrag;
import com.example.snoee.myapplistview.adapters.CustomAdapter;
import com.example.snoee.myapplistview.fragments.DevFrag;
import com.example.snoee.myapplistview.R;
import com.example.snoee.myapplistview.interfaces.Communicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Communicator {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MyPrefsFileMain";
    static String dir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        dir = prefs.getString("directions", "none");


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        ListView listView = findViewById(R.id.category_list);
        listView.setDividerHeight(15);
        listView.setAdapter(new CustomAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] titles = MainActivity.this.getResources().getStringArray(R.array.titles);
                functionLauncher(titles[position]);
            }
        });
    }

    public void functionLauncher(String title) {
        Intent intent = new Intent(this, ControlPanel.class);
        intent.putExtra("directions", "");

        switch (title) {
            case "Control Panel":
                intent.putExtra("functionKey", 0);
                break;
            case "Statistics":
                intent.putExtra("functionKey", 1);
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.settings_id:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_LONG).show();
//                AutoCutDialogFrag autoFrag = new AutoCutDialogFrag();
//                autoFrag.show(getFragmentManager(), "auto frag");
                break;
            case R.id.status_check_id:
                Toast.makeText(getApplicationContext(), "status check", Toast.LENGTH_LONG).show();
                break;
            case R.id.developer_id:
                DevFrag devFrag = new DevFrag();
                devFrag.show(getFragmentManager(), "dev frag");
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings_id) {
            Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
//            AutoCutDialogFrag autoFrag = new AutoCutDialogFrag();
//            autoFrag.show(getFragmentManager(), "auto frag");
        } else if (id == R.id.menu_developer_id) {
            DevFrag devFrag = new DevFrag();
            devFrag.show(getFragmentManager(), "dev frag");
        } else if (id == R.id.item3_id) {
            Toast.makeText(getApplicationContext(), "item3 is selected", Toast.LENGTH_SHORT).show();
            finish();
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogMessage(String tag, String message) {
        switch (tag) {
            case "AutoCutDialogFrag":
                Toast.makeText(this, (message), Toast.LENGTH_SHORT).show();
            case "DedicateDirectionsDialogFrag":
                Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_LONG).show();
                editor.putString("directions", message + "");
                editor.apply();
                dir = message + "";
                break;
            default:
                System.out.println("no match");
        }
    }
}

