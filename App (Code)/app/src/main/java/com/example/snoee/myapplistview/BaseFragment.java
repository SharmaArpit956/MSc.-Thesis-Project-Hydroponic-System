package com.example.snoee.myapplistview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    public String[] outputValues;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = provideYourFragmentView(inflater, parent, savedInstanseState);
        return view;
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public abstract String getFragmentName();

    public abstract String getWindDirection();


    public abstract void setBattery(int bat);

    public abstract void setSeekPin(int i);

    public abstract void setphValue(double i);

    public abstract void setphStatus(String status);

    public abstract void setLiquidLevelTv(double ll) ;

    public abstract void displayScheduleTv(String str);

 }
