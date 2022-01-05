package com.example.snoee.myapplistview.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.snoee.myapplistview.AutoCutDialogFrag;
import com.example.snoee.myapplistview.BaseFragment;
import com.example.snoee.myapplistview.fragments.ControlPanelFrag;
import com.example.snoee.myapplistview.adapters.ViewPagerAdapter;
import com.example.snoee.myapplistview.fragments.StatisticsFrag;
import com.example.snoee.myapplistview.interfaces.Communicator;
import com.example.snoee.myapplistview.fragments.DevFrag;
import com.example.snoee.myapplistview.R;
import com.example.snoee.myapplistview.fragments.WeatherFrag;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ControlPanel extends AppCompatActivity implements Communicator {
    MQTThelper mqttHelper;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    int[][] colors = {{R.color.ColorPrimaryLight_controlPanel, R.color.ColorPrimary_controlPanel, R.color.ColorPrimaryDark_controlPanel, R.color.ColorAccent_controlPanel},
            {R.color.ColorPrimaryLight_statistics, R.color.ColorPrimary_statistics, R.color.ColorPrimaryDark_statistics, R.color.ColorAccent_statistics}};

    String schedule;
    BaseFragment page;
    List<String> itemsSelected;
    Set<String> set;
    String message;

    public static final String MY_PREFS_NAME = "MyPrefsFileControl";
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        toolbar = findViewById(R.id.control_panel_toolbar);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewPager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ControlPanelFrag(), "CONTROL PANEL");
        adapter.addFragment(new StatisticsFrag(), "STATISTICS");

        viewPager.setAdapter(adapter);

        tabLayoutInit();
        startMqtt();

        itemsSelected = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int i = bundle.getInt("functionKey");
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Objects.requireNonNull(tab).select();
            toolbarAndTabGradient(colors[i][2]);
            changeTabTextColor(bundle.getInt("functionKey"));
        }
    }

    private void tabLayoutInit() {
        tabLayout = findViewById(R.id.tabLayout_id);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.control_panel_icon);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.statistics_icon);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabTextColor(tab.getPosition());
                toolbarAndTabGradient(colors[tab.getPosition()][2]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Toast.makeText(ControlPanel.this, tab.getText() + " onTabUnselected ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Toast.makeText(ControlPanel.this, tab.getText() + " onTabReselected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startMqtt() {
        mqttHelper = new MQTThelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
            }

            @Override
            public void connectionLost(Throwable throwable) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                page = (BaseFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager_id + ":" + viewPager.getCurrentItem());
                if (page != null) {
                    if (topic.equals(mqttHelper.batteryFeed)) {
                        page.setBattery(Integer.parseInt(mqttMessage.toString()));
                    }
                    if (topic.equals(mqttHelper.pumpSpeedFeed)) {
                        page.setSeekPin(Integer.parseInt(mqttMessage.toString()));
                    }
                    if (topic.equals(mqttHelper.pHFeed)) {
                        page.setphValue(Double.parseDouble(mqttMessage.toString()));
                    }
                    if (topic.equals(mqttHelper.statusFeed)) {
                        page.setphStatus(mqttMessage.toString());
                    }
                    if (topic.equals(mqttHelper.liquidLevelFeed)) {
                        page.setLiquidLevelTv(Double.parseDouble(mqttMessage.toString()));
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }

    public void pub(String topic, String message) {
        try {
            mqttHelper.mqttAndroidClient.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogMessage(String tag, String msg) {
        message = msg;
        switch (tag) {

            case "PumpSliderFrag":
                pub(mqttHelper.pumpSpeedFeed, message);
                break;
            case "DevFrag":
                Toast.makeText(this, (message), Toast.LENGTH_SHORT).show();
                break;

            case "CityWeatherFrag":
                Toast.makeText(this, ("new city is " + message), Toast.LENGTH_SHORT).show();
                WeatherFrag.updateCity(message);
                break;

            case "SpeedPickerFrag":
                Toast.makeText(this, message + "", Toast.LENGTH_SHORT).show();
                schedule += "speed: " + message + " , ";
                break;

            case "SpeedPickerFromSetupFrag":
                Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_LONG).show();
                page = (BaseFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager_id + ":" + viewPager.getCurrentItem());
                if (page != null) {
                    page.setSeekPin(Integer.parseInt(message));
                }
                // pub(mqttHelper.pumpFeed, message);
                break;
            default:
                System.out.println("no match");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void toolbarAndTabGradient(int colorA) {
        int[] colors = {ContextCompat.getColor(getApplicationContext(), colorA), ContextCompat.getColor(getApplicationContext(), R.color.Black)};
        //create a new gradient color
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gd.setCornerRadius(0f);
        //apply the button background to newly created drawable gradient
        toolbar.setBackground(gd);
        tabLayout.setBackground(gd);
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

    public void changeTabTextColor(int customColor) {
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getApplicationContext(), R.color.white),
                ContextCompat.getColor(getApplicationContext(), colors[customColor][3])
        );
    }

}

