package com.example.snoee.myapplistview.fragments;

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
import android.widget.TextView;

import com.example.snoee.myapplistview.R;

public class phFrag extends Fragment {
    TextView phValueTv, phStatusTv;
    public double phValue;
    public String phStatus;

    private @StyleRes
    int themeResId;
    private static final int NO_CUSTOM_THEME = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ↓↓↓
        if (themeResId != NO_CUSTOM_THEME) {
            inflater = inflater.cloneInContext(
                    new ContextThemeWrapper(getActivity(), themeResId)
            );
        }
        // ↑↑↑
        View view = inflater.inflate(R.layout.fragment_ph, container, false);
        phValueTv = view.findViewById(R.id.ph_value);
        phStatusTv = view.findViewById(R.id.ph_status);
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

    public void setPhValueTv(double ph) {
        phValue = ph;
        if (phValueTv != null) {
            phValueTv.setText(String.valueOf(ph));
        }
    }

    public void setPhStatusTv(String status) {
        phStatus = status;
        if (phStatusTv != null) {
            phStatusTv.setText(String.valueOf(status));
        }
    }
}
