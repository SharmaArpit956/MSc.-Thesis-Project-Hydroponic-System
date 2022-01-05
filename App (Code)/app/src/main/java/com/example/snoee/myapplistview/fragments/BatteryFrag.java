package com.example.snoee.myapplistview.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoee.myapplistview.R;


public class BatteryFrag extends Fragment {
    public TextView backupStatusTv;
    public TextView estimatedRunningTimeTv;
    public TextView batteryTv;
    public ImageView battLevel;
    public ImageView battIcon;

    public int battery = 100;
    private int estimatedTimeRemaining = -1;
    public View v;
    static ViewGroup.LayoutParams layoutParams;
    private @StyleRes
    int themeResId;
    private static final int NO_CUSTOM_THEME = 0;
    int batteryLevelWidth;
    private static final double battToTimeConversionFactor = 0.02;
    private static final String estimatedTimeRemainingPrefix = "Estimated Time:";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (themeResId != NO_CUSTOM_THEME) {
            inflater = inflater.cloneInContext(
                    new ContextThemeWrapper(getActivity(), themeResId)
            );
        }
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        backupStatusTv = view.findViewById(R.id.backup_status_id);
        estimatedRunningTimeTv = view.findViewById(R.id.estimated_running_time_id);
        battLevel = view.findViewById(R.id.batt_level_id);
        battLevel.post(new Runnable() {
            @Override
            public void run() {
                batteryLevelWidth = battLevel.getMeasuredWidth();
            }
        });
        battIcon = view.findViewById(R.id.battery_icon_id);
        battIcon.post(new Runnable() {
            @Override
            public void run() {
                battIcon.setAlpha(100);
            }
        });

        batteryTv = view.findViewById(R.id.batt);
        if(battery!=-1) {
            batteryTv.setText(String.valueOf(battery) + "%");
        }
        v = view;
        return view;
    }

    @Override
    public void onInflate(
            @NonNull Context context,
            AttributeSet attrs,
            Bundle savedInstanceState
    ) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ChildFragment
        );
        themeResId = a.getResourceId(
                R.styleable.ChildFragment_customTheme,
                NO_CUSTOM_THEME
        );
        a.recycle();
    }

    public void setBatteryTv(int batt) {
        Toast.makeText(getActivity(), "changed", Toast.LENGTH_LONG);
        battery = batt;
        if (batteryTv != null&&battery!=-1) {
            batteryTv.setText(String.valueOf(battery) + "%");
            layoutParams = battLevel.getLayoutParams();
            layoutParams.width = (int) (Double.valueOf(batteryLevelWidth / 100.0) * Double.valueOf(batt));
            battLevel.setLayoutParams(layoutParams);
            estimatedTimeRemaining = (int) (batt * battToTimeConversionFactor * 60);
            estimatedRunningTimeTv.setText(estimatedTimeRemainingPrefix + String.valueOf(estimatedTimeRemaining / 60) + " hours" + " , " + String.valueOf(estimatedTimeRemaining % 60) + " minutes");
        }
    }
}
