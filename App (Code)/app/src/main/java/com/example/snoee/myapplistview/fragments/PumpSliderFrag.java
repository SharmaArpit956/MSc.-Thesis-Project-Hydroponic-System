package com.example.snoee.myapplistview.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.snoee.myapplistview.interfaces.Communicator;
import com.example.snoee.myapplistview.R;

public class PumpSliderFrag extends Fragment {
    Communicator communicator;
    TextView pumpSpeed;
    SeekBar pumpSlider;
    int seekPin;
    private @StyleRes
    int themeResId;
    private static final int NO_CUSTOM_THEME = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //isto nao vai funcionar se a nossa actividade nao implementar interface
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ↓↓↓
        if (themeResId != NO_CUSTOM_THEME) {
            inflater = inflater.cloneInContext(
                    new ContextThemeWrapper(getActivity(), themeResId)
            );
        }
        // ↑↑↑
        View view = inflater.inflate(R.layout.fragment_pump_slider, null);
        pumpSpeed = view.findViewById(R.id.pumpSpeed);
        pumpSlider = view.findViewById(R.id.pumpSlider);
        pumpSliderInit();
        return view;
    }

    public void pumpSliderInit() {

        pumpSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPin = progress;
                pumpSpeed.setText("Pump Speed : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                communicator.onDialogMessage("PumpSliderFrag", "" + seekPin);
            }
        });
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

    public void setSeekPinPs(int i) {
        pumpSlider.setProgress(i);
    }
}
