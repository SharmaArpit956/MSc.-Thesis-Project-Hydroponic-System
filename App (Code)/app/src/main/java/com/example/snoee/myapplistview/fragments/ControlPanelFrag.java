package com.example.snoee.myapplistview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snoee.myapplistview.BaseFragment;
import com.example.snoee.myapplistview.R;

public class ControlPanelFrag extends BaseFragment {
    View view;
    Bundle bundle;
    BatteryFrag battFrag;
    WeatherFrag wFrag;
    PumpSliderFrag psFrag;

    public String[] outputValues;
    String inputValue;

    public String getInputValue() {
        return inputValue;
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_panel, parent, false);
        return view;
    }

    @Override
    public String getFragmentName() {
        return "AgroFrag";
    }

    @Override
    public String getWindDirection() {
        WeatherFrag weathtFrag = (WeatherFrag) getChildFragmentManager().findFragmentById(R.id.weath_id);
        return weathtFrag.getWindDirection();
    }

    @Override
    public void setBattery(int bat) {
        BatteryFrag battFrag = (BatteryFrag) getChildFragmentManager().findFragmentById(R.id.batt_id);
        battFrag.setBatteryTv(bat);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setSeekPin(int i) {
        PumpSliderFrag psFrag = (PumpSliderFrag) getChildFragmentManager().findFragmentById(R.id.ps_id);
        psFrag.setSeekPinPs(i);
    }

    @Override
    public void setphValue(double i) {
        phFrag pf = (phFrag) getChildFragmentManager().findFragmentById(R.id.ph_id);
        pf.setPhValueTv(i);
    }

    @Override
    public void setphStatus(String i) {
        phFrag pf = (phFrag) getChildFragmentManager().findFragmentById(R.id.ph_id);
        pf.setPhStatusTv(i);
    }

    @Override
    public void setLiquidLevelTv(double ll) {
        LiquidLevelFrag llf = (LiquidLevelFrag) getChildFragmentManager().findFragmentById(R.id.liquid_level_id);
        llf.setLiquidLevelTv(ll);
    }

    @Override
    public void displayScheduleTv(String str) {
    }
}
