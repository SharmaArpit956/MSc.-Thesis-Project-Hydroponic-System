package com.example.snoee.myapplistview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snoee.myapplistview.BaseFragment;
import com.example.snoee.myapplistview.R;

public class StatisticsFrag extends BaseFragment {
    View view;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, parent, false);
        return view;
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public String getWindDirection() {
        return null;
    }

    @Override
    public void setBattery(int bat) {

    }

    @Override
    public void setSeekPin(int i) {

    }

    @Override
    public void setphValue(double i) {

    }

    @Override
    public void setphStatus(String i) {

    }

    @Override
    public void setLiquidLevelTv(double ll) {

    }

    @Override
    public void displayScheduleTv(String str) {

    }

}

