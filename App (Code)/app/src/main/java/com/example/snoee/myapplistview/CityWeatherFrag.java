package com.example.snoee.myapplistview;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.snoee.myapplistview.interfaces.Communicator;


public class CityWeatherFrag extends DialogFragment implements View.OnClickListener {

    TextView currentCityTv;
    EditText changeCityTv;
    Button ok;
    Button skip;
    Button cancel;

    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_weather, container, false);
        currentCityTv = (TextView) v.findViewById(R.id.current_city_id);
        changeCityTv = (EditText) v.findViewById(R.id.change_city_id);
        ok = (Button) v.findViewById(R.id.ok_btn);
        skip = (Button) v.findViewById(R.id.skip_btn);
        cancel = (Button) v.findViewById(R.id.canel_btn);
        ok.setOnClickListener(this);
        skip.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                //Display the newly selected value from picker
                communicator.onDialogMessage("CityWeatherFrag", ""+changeCityTv.getText());
                dismiss();
                break;
            case R.id.skip_btn:
                dismiss();
                break;
            case R.id.canel_btn:
                dismiss();
                break;
        }
    }

}
