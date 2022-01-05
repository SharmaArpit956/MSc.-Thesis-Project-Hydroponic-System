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
import android.widget.Toast;

import com.example.snoee.myapplistview.interfaces.Communicator;


public class AutoCutDialogFrag extends DialogFragment implements View.OnClickListener {
    Button sendFeedback;
    EditText feedbackEt;

    Button ok;
    Button skip;
    Button cancel;

    private int selectedValue=1;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto_cut_dialog, container, false);
        ok = (Button) v.findViewById(R.id.ok_btn);
        skip = (Button) v.findViewById(R.id.skip_btn);
        cancel = (Button) v.findViewById(R.id.canel_btn);
        ok.setOnClickListener(this);
        skip.setOnClickListener(this);
        cancel.setOnClickListener(this);

        NumberPicker np = (NumberPicker) v.findViewById(R.id.np);
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker

                selectedValue = newVal;
            }
        });
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                //Display the newly selected value from picker
                communicator.onDialogMessage("AutoCutDialogFrag","" + selectedValue);
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
