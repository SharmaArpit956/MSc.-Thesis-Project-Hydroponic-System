package com.example.snoee.myapplistview.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.snoee.myapplistview.R;
import com.example.snoee.myapplistview.interfaces.Communicator;

public class SpeedPickerFromSetupFrag extends DialogFragment implements View.OnClickListener {
    Button ok;
    Button cancel;
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
        View view = inflater.inflate(R.layout.fragment_speed_picker_from_setup, null);
        pumpSpeed = view.findViewById(R.id.pumpSpeed);
        pumpSlider = view.findViewById(R.id.pumpSlider);
        ok = (Button) view.findViewById(R.id.ok_btn);
        cancel = (Button) view.findViewById(R.id.canel_btn);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        pumpSliderInit();
        return view;
    }

    public void pumpSliderInit() {

        pumpSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Toast.makeText(getActivity(), "seekbar progress: " + progress, Toast.LENGTH_SHORT).show();
                seekPin = progress;
                pumpSpeed.setText("Pump Speed : " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getActivity(), "seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getActivity(), "seekbar touch stopped!", Toast.LENGTH_SHORT).show();
//                communicator.onDialogMessage("SpeedPickerFromSetupFrag", "" + seekPin);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                //Display the newly selected value from picker
                communicator.onDialogMessage("SpeedPickerFromSetupFrag",seekPin+"");
                dismiss();
                break;
            case R.id.canel_btn:
                dismiss();
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout( getResources().getDimensionPixelSize(R.dimen.dialog_width), getResources().getDimensionPixelSize(R.dimen.dialog_height));
    }
}
